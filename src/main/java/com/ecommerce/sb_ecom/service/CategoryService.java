package com.ecommerce.sb_ecom.service;

import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.CategoryReponse;

@Service
public interface CategoryService {
    CategoryReponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    public CategoryDTO deleteCategory(Long categoryId);
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
