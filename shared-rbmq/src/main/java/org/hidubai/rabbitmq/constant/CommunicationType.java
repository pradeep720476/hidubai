package org.hidubai.rabbitmq.constant;

import lombok.Data;

import java.io.Serializable;

public enum CommunicationType implements Serializable {
    SMS,
    PUSH_NOTIFICATION,
    WHATSAPP,
}
