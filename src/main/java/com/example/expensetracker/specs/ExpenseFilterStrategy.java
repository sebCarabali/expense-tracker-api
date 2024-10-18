package com.example.expensetracker.specs;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.expensetracker.model.Expense;

public interface ExpenseFilterStrategy {
    Specification<Expense> apply(LocalDate start, LocalDate end);
}
