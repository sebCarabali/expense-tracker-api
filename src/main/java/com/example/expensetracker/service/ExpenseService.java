package com.example.expensetracker.service;

import java.time.LocalDate;
import java.util.List;

import com.example.expensetracker.dto.expenses.ExpenseRequestDTO;
import com.example.expensetracker.dto.expenses.ExpenseResponseDTO;

public interface ExpenseService {

    ExpenseResponseDTO create(ExpenseRequestDTO request);

    void delete(Long id);

    ExpenseResponseDTO update(Long id, ExpenseRequestDTO requestDTO);

    List<ExpenseResponseDTO> findAllBy(String filter, LocalDate start, LocalDate end);
}
