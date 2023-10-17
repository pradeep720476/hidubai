package org.hidubai.publisher.handler;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.hidubai.publisher.dto.PublisherRequest;
import org.hidubai.publisher.dto.PublisherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

import static org.hidubai.publisher.constants.ErrorCode.VALIDATION_ERROR_10001;

@ControllerAdvice
public class RequestValidationExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestValidationExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public PublisherResponse handleConstraintViolationException(MethodArgumentNotValidException exception) {
        LOGGER.debug("Error Response: {}", exception.getCause());
        return PublisherResponse.builder()
                .code(String.valueOf(VALIDATION_ERROR_10001))
                .message(messageHelper(exception.getBindingResult().getFieldErrors()))
                .build();
    }

    private String messageHelper(List<FieldError> fieldErrors) {
        String message = null;
        for(FieldError error : fieldErrors){
            message = error.getDefaultMessage();
        }
        return message;
    }

}