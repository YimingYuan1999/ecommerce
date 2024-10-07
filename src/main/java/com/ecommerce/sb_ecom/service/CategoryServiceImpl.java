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

import com.ecommerce.sb_ecom.repository.CategoryRepository;
import com.ecommerce.sb_ecom.model.Category;
import com.sun.net.httpserver.HttpsConfigurator;

@Service
public class CategoryServiceImpl implements CategoryService{

    // private List<Category> categories = new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
        // List<Category> categories = categoryRepository.findAll();
        // Category category = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst()
        //                                 .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
        categoryRepository.delete(category);
        return "Delete categoryId: " + categoryId + " category";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        // List<Category> categories = categoryRepository.findAll();
        Category savedCategory = categoryRepository.findById(categoryId)
                                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found"));
        category.setCategoryId(categoryId);

        // Optional<Category> optionalCategory = categories.stream().filter(c -> c.getCategoryId().equals(categoryId)).findFirst();
        // if (optionalCategory.isPresent()){
        //     Category existingCategory = optionalCategory.get();
        //     existingCategory.setCategoryName(category.getCategoryName());
        //     Category savedCategory = categoryRepository.save(existingCategory);
        //     return savedCategory;
        // }else{
        //     throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource Not Found");
        // }
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
    
}   
