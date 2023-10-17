package org.hidubai.javasubscriber.notification;

import org.hidubai.rabbitmq.constant.ClientChannel;
import org.hidubai.rabbitmq.constant.CommunicationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PushNotificationService implements Notification {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushNotificationService.class);

    @Override
    public void sendToMobile(ClientChannel clientChannel, Integer leadId) {
        LOGGER.info("Subscriber 2: [Channel: {}] [LeadID: {}] [Message: {} }]", clientChannel, leadId, "Push Notification Sent");
    }
}
