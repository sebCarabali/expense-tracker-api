package com.example.expensetracker.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.expensetracker.dto.expenses.ExpenseRequestDTO;
import com.example.expensetracker.dto.expenses.ExpenseResponseDTO;
import com.example.expensetracker.exception.ExpenseAPIException;
import com.example.expensetracker.mapper.ExpenseMapper;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.model.User;
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.specs.ExpenseFilterRegistry;
import com.example.expensetracker.specs.ExpenseFilterStrategy;
import com.example.expensetracker.specs.ExpenseSpecification;
import com.example.expensetracker.util.AuthUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultExpenseService implements ExpenseService {

    private final ExpenseMapper expenseMapper;
    private final ExpenseRepository expenseRepository;
    private final ExpenseFilterRegistry expenseFilterRegistry;
    private final AuthUtil authUtil;

    @Override
    @Transactional
    public ExpenseResponseDTO create(ExpenseRequestDTO request) {
        User user = authUtil.getCurrentUser();
        Expense expense = expenseMapper.requestDTOToExpense(request);
        expense.setUser(user);
        try {
            expense = expenseRepository.save(expense);
            return expenseMapper.expenseToResponseDTO(expense);
        } catch (Exception e) {
            log.error("Error saving expense for user {}: {}", user.getId(), e.getMessage());
            throw new ExpenseAPIException(500, "Internal server error", "Could not save expense");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Expense expense = getExpenseByIdAndValidateOwnership(id);
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional
    public ExpenseResponseDTO update(Long id, ExpenseRequestDTO requestDTO) {
        Expense expense = getExpenseByIdAndValidateOwnership(id);
        expenseMapper.updateFiled(expense, requestDTO);
        return expenseMapper.expenseToResponseDTO(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseResponseDTO> findAllBy(String filter, LocalDate start, LocalDate end) {
        User user = authUtil.getCurrentUser();
        ExpenseFilterStrategy expenseFilterStrategy = expenseFilterRegistry.getFilterStrategy(filter);
        
        if (expenseFilterStrategy != null) {
            return expenseRepository
                    .findAll(expenseFilterStrategy.apply(start, end)
                            .and(ExpenseSpecification.hasUserId(user.getId())))
                    .stream()
                    .map(expenseMapper::expenseToResponseDTO)
                    .toList();
        }

        return expenseRepository.findAll(ExpenseSpecification.hasUserId(user.getId()))
                .stream()
                .map(expenseMapper::expenseToResponseDTO)
                .toList();
    }

    private Expense getExpenseByIdAndValidateOwnership(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> createException(404, "Expense not found"));

        User user = authUtil.getCurrentUser();
        if (!expense.getUser().getId().equals(user.getId())) {
            throw createException(403, "You're not allowed to perform this action");
        }

        return expense;
    }

    private ExpenseAPIException createException(int statusCode, String message) {
        log.warn("Exception occurred: {} (status code: {})", message, statusCode);
        return new ExpenseAPIException(statusCode, "Error", message);
    }
}
