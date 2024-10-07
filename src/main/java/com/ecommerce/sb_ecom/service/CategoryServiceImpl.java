package com.ecommerce.sb_ecom.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.sb_ecom.exceptions.APIException;
import com.ecommerce.sb_ecom.repository.CategoryRepository;
import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Category;
import com.sun.net.httpserver.HttpsConfigurator;

@Service
public class CategoryServiceImpl implements CategoryService{

    // private List<Category> categories = new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new APIException("No category find!");
        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category saveCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (saveCategory != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
        }
        this.categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                                        .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
        
        categoryRepository.delete(category);
        return "Delete categoryId: " + categoryId + " category";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        // List<Category> categories = categoryRepository.findAll();
        Category savedCategory = categoryRepository.findById(categoryId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId",categoryId));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
    
}   
