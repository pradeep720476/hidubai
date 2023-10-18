package org.hidubai.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.hidubai.subscriber.factory.SubscriberFactory;
import org.hidubai.subscriber.notification.EmailNotificationService;
import org.hidubai.subscriber.notification.Notification;
import org.hidubai.rabbitmq.constant.ClientChannel;
import org.hidubai.rabbitmq.dto.MQRequest;
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
            Thread emailThread = new Thread(() -> {
                try {
                    while (true) {
                        Channel emailChannel = connection.createChannel();
                        emailChannel.queueBind(ClientChannel.Email.getChannelName(), "leads-direct-exchange", "email-channel");
                        emailChannel.basicConsume("email-channel", true, (consumerTag, message) -> {
                            String json = new String(message.getBody(), "UTF-8");
                            ObjectMapper objectMapper = new ObjectMapper();
                            MQRequest mqRequest = objectMapper.readValue(json, MQRequest.class);
                            new EmailNotificationService().sendToMobile(ClientChannel.Email, mqRequest.getLeadId());
                        }, consumerTag -> {
                        });
                        Thread.sleep(5000);
                    }
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Error while creating connection / connection got timeout for Email Channel", e.getMessage());
                }
            });
            Thread mobileThread = new Thread(() -> {
                try {
                    while (true) {
                        Channel mobileChannel = connection.createChannel();
                        mobileChannel.queueBind(ClientChannel.Mobile.getChannelName(), "leads-direct-exchange", "mobile-channel");
                        mobileChannel.basicConsume("mobile-channel", true, (consumerTag, message) -> {
                            String json = new String(message.getBody(), "UTF-8");
                            ObjectMapper objectMapper = new ObjectMapper();
                            MQRequest mqRequest = objectMapper.readValue(json, MQRequest.class);
                            Optional<Notification> notificationService = SubscriberFactory.getNotificationService(mqRequest.getCommunicationMode());
                            notificationService.ifPresent(service -> service.sendToMobile(ClientChannel.Mobile, mqRequest.getLeadId()));
                        }, consumerTag -> {
                        });
                        Thread.sleep(5000);
                    }
                } catch (IOException | InterruptedException e) {
                    LOGGER.error("Error while creating connection / connection got timeout for Mobile Channel", e.getMessage());
                }
            });
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
