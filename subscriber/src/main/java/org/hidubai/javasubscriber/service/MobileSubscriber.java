package org.hidubai.javasubscriber.service;

import org.hidubai.rabbitmq.dto.MQRequest;

public class MobileSubscriber implements Subscriber {
    @Override
    public void subscribe(MQRequest mqRequest) {
        System.out.println("MOBILE-CHANNEL");
    }
}
