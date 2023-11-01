package org.hidubai.publisher.constants;

import org.hidubai.publisher.dto.Error;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hidubai.publisher.constants.HttpCode.VALIDATION_ERROR_10001;

public class Helper {

    public static List<Error> errorHelper(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> Error.builder()
                        .code(VALIDATION_ERROR_10001.name())
                        .message(fieldError.getDefaultMessage()).build())
                .collect(Collectors.toList());
    }

    public static List<Error> errorHelper(String code, String message) {
        return Collections.singletonList(Error.builder().code(code).message(message).build());
    }

}
