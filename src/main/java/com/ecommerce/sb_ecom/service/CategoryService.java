package com.ecommerce.sb_ecom.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.ecommerce.sb_ecom.model.Category;

@Service
public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    public String deleteCategory(Long categoryId);
    public Category updateCategory(Long categoryId, Category category);
}
