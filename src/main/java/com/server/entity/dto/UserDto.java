package com.server.entity.dto;

import com.server.validation.EmailValidation;
import com.server.validation.PasswordValidation;
import com.server.validation.ValidationConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PasswordValidation.PasswordMatches
public class UserDto {
    @NotNull(message = "User name cannot be null")
    @NotEmpty
    private String name;

    @EmailValidation.ValidEmail
    @NotNull(message = "User email cannot be null")
    @NotEmpty
    private String email;

    @NotNull(message = "User password cannot be null")
    @NotEmpty
    private String password;
    private String matchingPassword;
}
