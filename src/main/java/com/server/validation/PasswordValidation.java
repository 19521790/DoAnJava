package com.server.validation;

import com.server.model.dto.UserDto;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class PasswordValidation {

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Constraint(validatedBy = PasswordMatchesValidator.class)
    @Documented
    public @interface PasswordMatches {
        String message() default "Passwords don't match";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

    public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
        @Override
        public void initialize(PasswordMatches constraintAnnotation) {
        }

        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext context) {
            UserDto user = (UserDto) obj;
            return user.getPassword().equals(user.getMatchingPassword());
        }
    }
}
