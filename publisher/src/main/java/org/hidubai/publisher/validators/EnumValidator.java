package org.hidubai.publisher.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue enumValue;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumValue = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null)
            return true;

        String[] enums = this.enumValue.values();
        for (String enumNames : enums) {
            if (enumNames.contains(value))
                return true;
        }
        return false;
    }

}
