package com.server.validation;

import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.*;
import javax.validation.constraints.Email;
import java.util.regex.Pattern;
import java.lang.annotation.*;
import java.util.regex.Matcher;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Configuration
public class ValidationConfig {
    @Bean
    public ValidatingMongoEventListener validationMongoEvenListener(){
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator(){
        return new LocalValidatorFactoryBean();
    }

}
