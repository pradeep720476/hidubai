package org.hidubai.subscriber.notification;

import org.hidubai.rabbitmq.constant.ClientChannel;

public interface Notification {

    void sendToMobile(ClientChannel clientChannel, Integer leadId);
}
