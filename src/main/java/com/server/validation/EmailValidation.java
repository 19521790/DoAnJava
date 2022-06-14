package com.server.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

public class EmailValidation {

    @Target({TYPE,FIELD, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Constraint(validatedBy = EmailValidator.class)
    @Documented
    public @interface ValidEmail{
        String message() default "Invalid email";
        Class<?>[] group() default {};
        Class<? extends Payload>[] payload() default {};
    }

    public class EmailValidator implements ConstraintValidator<ValidEmail,String> {
        private Pattern pattern;
        private Matcher matcher;
        private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        @Override
        public void initialize(ValidEmail constraintAnnotation) {
        }
        @Override
        public boolean isValid(String email, ConstraintValidatorContext context){
            return (validateEmail(email));
        }
        private boolean validateEmail(String email) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        }
    }
}
