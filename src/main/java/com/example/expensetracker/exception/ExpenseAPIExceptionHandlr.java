package com.example.expensetracker.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@ControllerAdvice
public class ExpenseAPIExceptionHandlr {

    @ExceptionHandler(ExpenseAPIException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ExpenseAPIException ex,
            WebRequest webRequest) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type("https://example.com/api-error")
                .title(ex.getError())
                .status(ex.getStatus())
                .detail(ex.getMessage())
                .instance(webRequest.getDescription(false))
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex,
            WebRequest request) {

        List<InvalidParam> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new InvalidParam(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .title("Validation Error") 
                .status(HttpStatus.BAD_REQUEST.value())
                .detail("One or more fields are invalid.")
                .instance(request.getDescription(false))
                .timestamp(LocalDateTime.now())
                .invalidParams(validationErrors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @AllArgsConstructor
    @Data
    @Builder
    private static class ErrorResponse {
        private String type;
        private String title;
        private int status;
        private String detail;
        private String instance;
        private LocalDateTime timestamp;
        private List<InvalidParam> invalidParams;
    }

    @AllArgsConstructor
    @Data
    @Builder
    private static class InvalidParam {
        private String field;
        private String message;
    }
}
