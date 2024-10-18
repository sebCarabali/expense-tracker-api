package com.example.expensetracker.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ExpenseAPIException extends RuntimeException {

    private final int status;
    private final String error;
    private final String message;
}
