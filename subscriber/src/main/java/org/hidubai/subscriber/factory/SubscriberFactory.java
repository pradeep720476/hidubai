package org.hidubai.subscriber.factory;

import org.hidubai.subscriber.notification.*;
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
            case EMAIL:
                return Optional.of(new EmailNotificationService());
        }
        return Optional.empty();
    }
}
