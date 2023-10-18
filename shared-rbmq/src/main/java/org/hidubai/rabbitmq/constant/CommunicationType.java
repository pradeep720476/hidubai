package org.hidubai.rabbitmq.constant;

import java.io.Serializable;
import java.util.stream.Stream;

public enum CommunicationType implements Serializable {
    SMS,
    PUSH_NOTIFICATION,
    WHATSAPP;

    public static String[] availableValues() {
        return Stream.of(values()).map(type -> type.name()).toArray(String[]::new);
    }
}
