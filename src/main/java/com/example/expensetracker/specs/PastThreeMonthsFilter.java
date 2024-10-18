package com.example.expensetracker.specs;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.expensetracker.model.Expense;

public class PastThreeMonthsFilter implements ExpenseFilterStrategy {

    @Override
    public Specification<Expense> apply(LocalDate start, LocalDate end) {
        LocalDate now = LocalDate.now();
        return (root, cq, cb) -> cb.between(root.get("date"), now.minusMonths(3), now);
    }

}
