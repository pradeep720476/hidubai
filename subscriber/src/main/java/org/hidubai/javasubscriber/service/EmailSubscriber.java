package org.hidubai.javasubscriber.service;


import org.hidubai.rabbitmq.dto.MQRequest;

public class EmailSubscriber implements Subscriber {

    @Override
    public void subscribe(MQRequest mqRequest) {
        System.out.println("EMAIL-CHANNEL");
    }
}
