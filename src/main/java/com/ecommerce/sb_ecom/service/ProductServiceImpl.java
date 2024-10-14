package com.ecommerce.sb_ecom.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.sb_ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.repository.CategoryRepository;
import com.ecommerce.sb_ecom.repository.ProductRepository;
import com.ecommerce.sb_ecom.model.Category;
import com.ecommerce.sb_ecom.payload.ProductReponse;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        product.setImage("default");
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        Product saveProduct = productRepository.save(product);
        return modelMapper.map(saveProduct,ProductDTO.class);
    }

    @Override
    public ProductReponse getAllProducts() {
        List<Product> products =  productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductReponse productReponse = new ProductReponse();
        productReponse.setContent(productDTOs);
        return productReponse;
    }

    @Override
    public ProductReponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOs = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductReponse productReponse = new ProductReponse();
        productReponse.setContent(productDTOs);
        return productReponse;
    }

    @Override
    public ProductReponse searchProductByKeyword(String keyword) {
        // Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%'+ keyword + '%');
        List<ProductDTO> productDTOs = products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();
        ProductReponse productReponse = new ProductReponse();
        productReponse.setContent(productDTOs);
        return productReponse;
    }


}
