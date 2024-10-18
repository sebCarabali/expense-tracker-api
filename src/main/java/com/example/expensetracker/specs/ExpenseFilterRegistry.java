package com.example.expensetracker.specs;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ExpenseFilterRegistry {

    private final Map<String, ExpenseFilterStrategy> filterStrategies;

    public ExpenseFilterRegistry() {
        this.filterStrategies = Map.of(
                "pastWeek", new PastWeekFilter(),
                "pastMonth", new PastMonthFilter(),
                "pastThreeMonth", new PastThreeMonthsFilter(),
                "between", new BetweenFilter()
        );
    }

    public ExpenseFilterStrategy getFilterStrategy(String filter) {
        return filterStrategies.get(filter);
    }
}
