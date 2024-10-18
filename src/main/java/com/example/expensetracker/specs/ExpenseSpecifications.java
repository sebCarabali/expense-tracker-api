package com.example.expensetracker.specs;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.example.expensetracker.model.Expense;

/**
     * Utility class for creating specifications to filter expenses based on date ranges.
     */
public final class ExpenseSpecifications {

    private ExpenseSpecifications() {
    }

    /**
     * Creates a specification to filter expenses incurred in the past week.
     *
     * @return A specification to filter expenses for the past week.
     */
    public static Specification<Expense> pastWeek() {
        LocalDate now = LocalDate.now();
        return (root, cq, cb) -> cb.between(root.get("date"), now.minusWeeks(1), now);
    }

    /**
     * Creates a specification to filter expenses incurred in the past month.
     *
     * @return A specification to filter expenses for the past month.
     */
    public static Specification<Expense> pastMonth() {
        LocalDate now = LocalDate.now();
        return (root, cq, cb) -> cb.between(root.get("date"), now.minusMonths(1), now);
    }

    /**
     * Creates a specification to filter expenses incurred in the past three months.
     *
     * @return A specification to filter expenses for the past three months.
     */
    public static Specification<Expense> pastThreeMonth() {
        LocalDate now = LocalDate.now();
        return (root, cq, cb) -> cb.between(root.get("date"), now.minusMonths(3), now);
    }

    /**
     * Creates a specification to filter expenses between a given start and end date.
     *
     * @param start The start date.
     * @param end The end date.
     * @return A specification to filter expenses between the given dates.
     */
    public static Specification<Expense> between(LocalDate start, LocalDate end) {
        return (root, cq, cb) -> cb.between(root.get("date"), start, end);
    }
}
