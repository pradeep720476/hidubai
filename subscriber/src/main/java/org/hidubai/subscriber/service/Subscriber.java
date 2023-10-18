package org.hidubai.subscriber.service;


import org.hidubai.rabbitmq.dto.MQRequest;

public interface Subscriber {
    void subscribe(MQRequest mqRequest);
}
