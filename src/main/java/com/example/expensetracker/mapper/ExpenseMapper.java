package com.example.expensetracker.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.expensetracker.dto.expenses.ExpenseRequestDTO;
import com.example.expensetracker.dto.expenses.ExpenseResponseDTO;
import com.example.expensetracker.exception.ExpenseAPIException;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repository.CategoryRepository;

@Component
public class ExpenseMapper {

    @Autowired
    private CategoryRepository categoryRepository;

    public Expense requestDTOToExpense(ExpenseRequestDTO createExpenseDTO) {
        return Expense.builder()
                .date(createExpenseDTO.getDate())
                .description(createExpenseDTO.getDescription())
                .amount(createExpenseDTO.getAmount())
                .currency(createExpenseDTO.getCurrency())
                .category(categoryRepository.findById(createExpenseDTO.getCategoryId())
                        .orElseThrow(() -> new ExpenseAPIException(400, "Bad Request", "Category not found")))
                .paymentMethod(createExpenseDTO.getPaymentMethod())
                .build();
    }

    public ExpenseResponseDTO expenseToResponseDTO(Expense expense) {
        return ExpenseResponseDTO.builder()
                .id(expense.getId())
                .date(expense.getDate())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .currency(expense.getCurrency())
                .category(expense.getCategory().getName())
                .paymentMethod(expense.getPaymentMethod())
                .build();
    }

    public void updateFiled(Expense expense, ExpenseRequestDTO requestDTO) {
        expense.setDescription(requestDTO.getDescription());
        expense.setPaymentMethod(requestDTO.getPaymentMethod());
        expense.setDate(requestDTO.getDate());
        expense.setCurrency(requestDTO.getCurrency());
        expense.setAmount(requestDTO.getAmount());
        if (expense.getCategory().getId() != requestDTO.getCategoryId()) {
            expense.setCategory(categoryRepository.findById(requestDTO.getCategoryId())
            .orElseThrow(() -> new ExpenseAPIException(400, "Bad Request", "Category not found")));
        }
    }
}
