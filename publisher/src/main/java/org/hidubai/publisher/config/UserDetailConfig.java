package org.hidubai.publisher.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "api.user")
@Data
public class UserDetailConfig {
    private String name;
    private String password;
}
