package org.hidubai.publisher.config;

import org.hidubai.publisher.handler.CustomeAccessDeniedExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Import(UserDetailConfig.class)
@EnableWebSecurity
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf
                        .ignoringRequestMatchers("/**"))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/leads/**")
                        .permitAll().anyRequest().authenticated())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new CustomeAccessDeniedExceptionHandler()))
                .httpBasic(Customizer.withDefaults()).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new CustomeAccessDeniedExceptionHandler()));
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService(UserDetailConfig userConfig) {
        UserDetails user = User.builder()
                .username(userConfig.getName())
                .password(userConfig.getPassword())
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
