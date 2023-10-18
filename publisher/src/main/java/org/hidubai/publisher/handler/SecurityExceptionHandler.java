package org.hidubai.publisher.handler;

import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import static org.hidubai.publisher.constants.HttpCode.AUTH_ERROR_20001;

@ControllerAdvice
public class SecurityExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(SecurityExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<PublisherResponse> accessDeniedExceptionHandler(AccessDeniedException accessDeniedException, @RequestBody(required = false) PublisherRequest publisherRequest) {
        LOGGER.error("Error Response: {}", accessDeniedException.getCause());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PublisherResponse.builder().lead_id(publisherRequest.getLeadId())
                .code(String.valueOf(AUTH_ERROR_20001.getCode()))
                .message(accessDeniedException.getMessage())
                .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<PublisherResponse> authenticationExceptionHandler(AuthenticationException authenticationException, @RequestBody(required = false) PublisherRequest publisherRequest) {
        LOGGER.error("Error Response: {}", authenticationException.getCause());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PublisherResponse.builder().lead_id(publisherRequest.getLeadId())
                .code(String.valueOf(AUTH_ERROR_20001.getCode()))
                .message(authenticationException.getMessage())
                .build());
    }
}
