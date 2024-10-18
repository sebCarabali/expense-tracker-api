package com.example.expensetracker.service;

import com.example.expensetracker.dto.users.JwtResponse;
import com.example.expensetracker.dto.users.LoginRequestDTO;
import com.example.expensetracker.dto.users.SingUpRequestDTO;

/**
 * AuthService interface handles user authentication operations
 * including sign-up and login functionality.
 */
public interface AuthService {

    /**
     * Registers a new user and generates a JWT token upon successful sign-up.
     * 
     * @param singUpRequest the DTO containing the user sign-up details such as 
     *                      username, password, email, etc.
     * @return a {@link JwtResponse} object containing the generated JWT token.
     */
    JwtResponse singUp(SingUpRequestDTO singUpRequest);

    /**
     * Authenticates an existing user and generates a JWT token upon successful login.
     * 
     * @param loginRequest the DTO containing the login credentials such as 
     *                     username and password.
     * @return a {@link JwtResponse} object containing the generated JWT token.
     */
    JwtResponse login(LoginRequestDTO loginRequest);
}
