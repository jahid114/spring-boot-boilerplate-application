package dev.jahid.user_auth_db_config_boilerplate.utility.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {})
@NotBlank( message = "Phone number is required" )
@Size( min = 11, max = 11, message = "Phone number must be 11 digits" )
@Pattern( regexp = "\\d{11}", message = "Phone number must contain only digits" )
@Target( { FIELD, METHOD, PARAMETER, ANNOTATION_TYPE } )
@Retention( RUNTIME )
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
