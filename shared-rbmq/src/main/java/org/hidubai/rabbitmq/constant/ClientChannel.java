package org.hidubai.rabbitmq.constant;

public enum ClientChannel {
    Email("email-channel"),
    Mobile("mobile-channel");

    private final String channelName;

    ClientChannel(String name) {
        this.channelName = name;
    }

    public String getChannelName() {
        return this.channelName;
    }

}
