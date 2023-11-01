package org.hidubai.publisher.handler;

import org.hidubai.publisher.constants.Helper;
import org.hidubai.publisher.dto.PublisherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.hidubai.publisher.constants.HttpCode.AUTH_ERROR_20001;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AccessDeniedExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<PublisherResponse> handleAccessDeniedExceptionHandler(Exception exception) {
        LOGGER.error("Error Response: {}", exception.getCause());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PublisherResponse.builder()
                .errors(Helper.errorHelper(String.valueOf(AUTH_ERROR_20001.getCode()), exception.getMessage())).build());
    }

}
