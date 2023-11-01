package org.hidubai.publisher.handler;

import org.hidubai.publisher.constants.Helper;
import org.hidubai.publisher.dto.PublisherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class RequestValidationExceptionHandler {
    public static final Logger LOGGER = LoggerFactory.getLogger(RequestValidationExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public PublisherResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        PublisherResponse response = PublisherResponse.builder().errors(Helper.errorHelper(exception.getBindingResult().getFieldErrors())).build();
        LOGGER.error("Error Response: {}",response );
        return response;
    }



    @Deprecated
    private String messageHelper(List<FieldError> fieldErrors) {
        String message = null;
        for (FieldError error : fieldErrors) {
            message = error.getDefaultMessage();
        }
        return message;
    }

}
