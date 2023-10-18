package org.hidubai.subscriber.factory;

import org.hidubai.subscriber.notification.Notification;
import org.hidubai.subscriber.notification.PushNotificationService;
import org.hidubai.subscriber.notification.SMSNotificationService;
import org.hidubai.subscriber.notification.WhatAppNotificationService;
import org.hidubai.rabbitmq.constant.CommunicationType;

import java.util.Optional;

public final class SubscriberFactory {
    public static final Optional<Notification> getNotificationService(CommunicationType communicationType) {
        switch (communicationType) {
            case SMS:
                return Optional.of(new SMSNotificationService());
            case WHATSAPP:
                return Optional.of(new WhatAppNotificationService());
            case PUSH_NOTIFICATION:
                return Optional.of(new PushNotificationService());
        }
        return Optional.empty();
    }
}
