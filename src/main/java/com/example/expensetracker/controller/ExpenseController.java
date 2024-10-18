package com.example.expensetracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.expensetracker.dto.expenses.ExpenseRequestDTO;
import com.example.expensetracker.dto.expenses.ExpenseResponseDTO;
import com.example.expensetracker.service.ExpenseService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> create(@Valid @RequestBody ExpenseRequestDTO request) {
        return new ResponseEntity<>(expenseService.create(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            Long id) {
        expenseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExpenseRequestDTO request) {
        return new ResponseEntity<>(expenseService.update(id, request), HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> find(@RequestParam(required = false, defaultValue = "noFilter") String filter,
            @RequestParam(required = false) LocalDate start,
            @RequestParam(required = false) LocalDate end) {
        return new ResponseEntity<>(expenseService.findAllBy(filter, start, end), HttpStatus.OK);
    }

}
