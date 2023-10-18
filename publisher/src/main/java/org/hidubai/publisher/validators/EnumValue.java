package org.hidubai.publisher.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValue {

    String message() default "Invalid enum value. Accepted values are {values}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    String[] values() ;
}
