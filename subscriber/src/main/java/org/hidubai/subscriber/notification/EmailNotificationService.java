package org.hidubai.subscriber.notification;

import org.hidubai.rabbitmq.constant.ClientChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationService implements Notification {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    @Override
    public void sendToMobile(ClientChannel clientChannel, Integer leadId) {
        LOGGER.info("Subscriber 1: [Channel: {}] [LeadID: {}] [Message: {} }]", clientChannel, leadId, "Email Sent");
    }
}
