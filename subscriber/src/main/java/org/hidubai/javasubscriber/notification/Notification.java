package org.hidubai.javasubscriber.notification;

import org.hidubai.rabbitmq.constant.ClientChannel;
import org.hidubai.rabbitmq.constant.CommunicationType;

public interface Notification {

    void sendToMobile(ClientChannel clientChannel, Integer leadId);
}
