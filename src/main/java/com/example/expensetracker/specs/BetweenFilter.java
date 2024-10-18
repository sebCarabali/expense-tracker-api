package com.example.expensetracker.specs;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.expensetracker.model.Expense;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BetweenFilter implements ExpenseFilterStrategy{

    @Override
    public Specification<Expense> apply(LocalDate start, LocalDate end) {
        return (root, cq, cb) -> cb.between(root.get("date"), start, end);
    }

}
