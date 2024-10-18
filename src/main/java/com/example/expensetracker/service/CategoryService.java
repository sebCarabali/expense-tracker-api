package com.example.expensetracker.service;

import java.util.List;
import com.example.expensetracker.model.Category;

public interface CategoryService {

    List<Category> findCategories();
}
