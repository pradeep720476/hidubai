package org.hidubai.subscriber.notification;


import org.hidubai.rabbitmq.constant.ClientChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatAppNotificationService implements Notification {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhatAppNotificationService.class);

    @Override
    public void sendToMobile(ClientChannel clientChannel, Integer leadId) {
        LOGGER.info("Subscriber 2: [Channel: {}] [LeadID: {}] [Message: {} }]", clientChannel.getChannelName(), leadId, "WhatsApp Message Sent");
    }
}
