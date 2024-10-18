package com.example.expensetracker.dto.users;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingUpRequestDTO implements Serializable {

    @NotEmpty(message = "username must not be empty")
    private String username;
    @NotEmpty(message = "email must not be empty")
    @Email
    private String email;
    @NotEmpty(message = "password must not be empty")
    private String password;
}
