package org.hidubai.publisher.handler;

import org.hidubai.publisher.dto.PublisherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.hidubai.publisher.constants.HttpCode.RABIT_MQ_ERROR_50001;

@ControllerAdvice
public class RabitMQExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(RabitMQExceptionHandler.class);

    @ExceptionHandler(AmqpException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public PublisherResponse handleRabbitMQException(AmqpException exception) {
        LOGGER.error("Error Response: {}", exception.getCause());
        return PublisherResponse.builder()
                .code(String.valueOf(RABIT_MQ_ERROR_50001.getCode()))
                .message(exception.getMessage())
                .build();
    }
}
