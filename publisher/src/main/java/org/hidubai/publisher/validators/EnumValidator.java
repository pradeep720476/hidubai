package org.hidubai.publisher.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private Class<? extends Enum> enumClass;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value))
            return true;

        try {
            Enum.valueOf(enumClass, value);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }

    }
}
