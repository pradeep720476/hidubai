package org.hidubai.publisher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf
                .ignoringRequestMatchers("/**")).authorizeHttpRequests(auth -> auth.requestMatchers("/**")
                .permitAll().anyRequest().authenticated());
        return httpSecurity.build();
    }
}
