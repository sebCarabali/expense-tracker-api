package com.example.expensetracker.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.expensetracker.config.security.JwtUtil;
import com.example.expensetracker.dto.users.JwtResponse;
import com.example.expensetracker.dto.users.LoginRequestDTO;
import com.example.expensetracker.dto.users.SingUpRequestDTO;
import com.example.expensetracker.exception.ExpenseAPIException;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.UserRepository;
import com.example.expensetracker.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public JwtResponse singUp(SingUpRequestDTO singUpRequest) {
        if (userRepository.findByUsername(singUpRequest.getUsername()).isPresent()) {
            throw new ExpenseAPIException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Username already exists.");
        }

        if (userRepository.findByEmail(singUpRequest.getEmail()).isPresent()) {
            throw new ExpenseAPIException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "email already exists.");
        }

        User user = User.builder()
                .email(singUpRequest.getEmail())
                .username(singUpRequest.getUsername())
                .password(passwordEncoder.encode(singUpRequest.getPassword()))
                .build();

        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ExpenseAPIException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Error saving the user.");
        }

        return new JwtResponse(jwtUtil.encode(user.getUsername()));
    }

    @Override
    @Transactional
    public JwtResponse login(LoginRequestDTO loginRequest) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtil.encode(loginRequest.getUsername());
            return new JwtResponse(token);

        } catch (BadCredentialsException e) {
            throw ExpenseAPIException.builder()
                    .error("Invalid Credentials")
                    .message("Invalid username or password.")
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .build();
        } catch (UsernameNotFoundException e) {
            throw ExpenseAPIException.builder()
                    .error("User Not Found")
                    .message("The specified user does not exist.")
                    .status(HttpStatus.NOT_FOUND.value())
                    .build();
        } catch (Exception e) {
            throw ExpenseAPIException.builder()
                    .error("Authentication Error")
                    .message("An error occurred during authentication.")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
        }
    }

}
