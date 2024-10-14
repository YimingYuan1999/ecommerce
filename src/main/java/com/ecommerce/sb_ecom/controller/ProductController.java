package com.ecommerce.sb_ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductReponse;
import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, 
                                                @PathVariable Long categoryId){

        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
                                                }
    
    @GetMapping("/public/products")
    public ResponseEntity<ProductReponse> getAllProducts(){
        ProductReponse productReponse =  productService.getAllProducts();
        return new ResponseEntity<>(productReponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductReponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductReponse productReponse = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productReponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductReponse> getProductByKeyword(@PathVariable String keyword){
        ProductReponse productReponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productReponse, HttpStatus.FOUND);
    }

}
