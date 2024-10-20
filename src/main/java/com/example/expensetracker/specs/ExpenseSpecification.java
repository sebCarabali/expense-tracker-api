package com.example.expensetracker.specs;

import org.springframework.data.jpa.domain.Specification;

import com.example.expensetracker.model.Expense;

public class ExpenseSpecification {

    public static Specification<Expense> hasUserId(Long userId) {
        return (root, cq, cb) -> cb.equal(root.get("user").get("id"), userId);
    }

}
