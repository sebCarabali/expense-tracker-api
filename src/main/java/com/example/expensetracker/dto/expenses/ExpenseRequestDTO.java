package com.example.expensetracker.dto.expenses;

import java.time.LocalDate;

import com.example.expensetracker.model.Category;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class ExpenseRequestDTO {

    @NotNull(message = "Date cannot be null.")
    @PastOrPresent(message = "Date must be in the past or present.")
    private LocalDate date;

    @NotEmpty(message = "Description cannot be empty.")
    private String description;

    @NotNull(message = "Amount cannot be null.")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero.")
    private Double amount;

    @NotEmpty(message = "Currency cannot be empty.")
    private String currency;

    @NotNull(message = "CategoryId cannot be null.")
    @Min(value = 1, message = "CategoryId must be greater than zero.")
    private Short categoryId;

    @NotNull(message = "Payment method cannot be null")
    private String paymentMethod;
}
