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
import com.example.expensetracker.repository.ExpenseRepository;
import com.example.expensetracker.service.ExpenseService;
import com.example.expensetracker.specs.ExpenseFilterRegistry;
import com.example.expensetracker.specs.ExpenseFilterStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultExpenseService implements ExpenseService {

    private final ExpenseMapper expenseMapper;
    private final ExpenseRepository expenseRepository;
    private final ExpenseFilterRegistry expenseFilterRegistry;

    @Override
    @Transactional
    public ExpenseResponseDTO create(ExpenseRequestDTO request) {
        Expense expense = expenseMapper.requestDTOToExpense(request);
        expense = expenseRepository.save(expense);
        return expenseMapper.expenseToResponseDTO(expense);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseAPIException(404, "Not found", "Expense not found"));
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponseDTO update(Long id, ExpenseRequestDTO requestDTO) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseAPIException(404, "Not found", "Expense not found"));
        expenseMapper.updateFiled(expense, requestDTO);
        return expenseMapper.expenseToResponseDTO(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseResponseDTO> findAllBy(String filter, LocalDate start, LocalDate end) {
        ExpenseFilterStrategy expenseFilterStrategy = expenseFilterRegistry.getFilterStrategy(filter);

        if (expenseFilterStrategy != null) {
            return expenseRepository.findAll(expenseFilterStrategy.apply(start, end)).stream().map(expenseMapper::expenseToResponseDTO).toList();
        }

        return expenseRepository.findAll().stream().map(expenseMapper::expenseToResponseDTO).toList();
    }

}
