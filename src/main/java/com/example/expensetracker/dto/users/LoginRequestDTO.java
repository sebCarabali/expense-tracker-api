package com.example.expensetracker.dto.users;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequestDTO implements Serializable {

    @NotEmpty(message = "username must not be null")
    private String username;
    @NotEmpty(message = "password must not be null")
    private String password;
}
