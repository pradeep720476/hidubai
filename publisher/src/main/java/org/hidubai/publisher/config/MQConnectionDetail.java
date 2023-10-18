package org.hidubai.publisher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rbmq")
@Data
@Component
public class MQConnectionDetail {

    private String exchangeName;
    private String emailChannelName;
    private String mobileChannelName;
}
