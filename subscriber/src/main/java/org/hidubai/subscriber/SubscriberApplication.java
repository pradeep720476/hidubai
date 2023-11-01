package org.hidubai.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.hidubai.rabbitmq.constant.ClientChannel;
import org.hidubai.rabbitmq.constant.CommunicationType;
import org.hidubai.rabbitmq.dto.MQRequest;
import org.hidubai.subscriber.factory.SubscriberFactory;
import org.hidubai.subscriber.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class SubscriberApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriberApplication.class);

    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("guest");
            connectionFactory.setPassword("guest");
            Connection connection = connectionFactory.newConnection();
            Thread emailThread = new Thread(new ChannelRunnable(ClientChannel.Email, "leads-direct-exchange", connection));
            Thread mobileThread = new Thread(new ChannelRunnable(ClientChannel.Mobile, "leads-direct-exchange", connection));
            mobileThread.setName("mobile-thread");
            mobileThread.setDaemon(true);
            emailThread.setName("email-thread");
            emailThread.setDaemon(true);
            mobileThread.start();
            emailThread.start();
            while (true) {
                LOGGER.info("Main Thread is Running");
                Thread.sleep(10000);
            }

        } catch (IOException | InterruptedException | TimeoutException e) {
            throw new RuntimeException(e);
        }

    }


}

class ChannelRunnable implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelRunnable.class);
    private ClientChannel queueName;
    private String exchangeName;

    private Connection connection;

    public ChannelRunnable(ClientChannel queueName, String exchangeName, Connection connection) {
        this.queueName = queueName;
        this.exchangeName = exchangeName;
        this.connection = connection;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                while (true) {
                    LOGGER.info("TheadName: {} running", Thread.currentThread().getName());
                    Channel mobileChannel = connection.createChannel();
                    mobileChannel.queueBind(this.queueName.getChannelName(), this.exchangeName, this.queueName.getChannelName());
                    mobileChannel.basicConsume(this.queueName.getChannelName(), true, (consumerTag, message) -> {
                        String json = new String(message.getBody(), "UTF-8");
                        ObjectMapper objectMapper = new ObjectMapper();
                        MQRequest mqRequest = objectMapper.readValue(json, MQRequest.class);
                        Optional<Notification> notificationService = SubscriberFactory.getNotificationService(this.queueName == ClientChannel.Email ? CommunicationType.EMAIL : mqRequest.getCommunicationMode());
                        notificationService.ifPresent(service -> service.sendToMobile(this.queueName, mqRequest.getLeadId()));
                    }, consumerTag -> {
                    });
                    Thread.sleep(5000);
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Error while creating connection / connection got timeout for Mobile Channel", e.getMessage());
            }
        }


    }
}
