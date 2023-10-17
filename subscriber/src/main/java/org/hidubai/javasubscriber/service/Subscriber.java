package org.hidubai.javasubscriber.service;


import org.hidubai.rabbitmq.dto.MQRequest;

public interface Subscriber {
    void subscribe(MQRequest mqRequest);
}
