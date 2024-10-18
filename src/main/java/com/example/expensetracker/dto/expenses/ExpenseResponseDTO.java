package com.example.expensetracker.dto.expenses;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseResponseDTO {

    private Long id;

    private LocalDate date;

    private String description;

    private Double amount;

    private String currency;

    private String category;

    private String paymentMethod;
}
