package com.ecommerce.sb_ecom.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.sb_ecom.payload.CategoryDTO;
import com.ecommerce.sb_ecom.payload.ProductDTO;
import com.ecommerce.sb_ecom.payload.ProductReponse;
import com.ecommerce.sb_ecom.model.Product;
import com.ecommerce.sb_ecom.service.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;



@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO, 
                                                @PathVariable Long categoryId){

        ProductDTO savedproductDTO = productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(savedproductDTO, HttpStatus.CREATED);
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

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long productId){
        ProductDTO updateProductDTO = productService.updateCategory(productId, productDTO);
        return new ResponseEntity<>(updateProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO deletedProductDTO = productService.deleteProduct(productId);
        return new ResponseEntity<>(deletedProductDTO, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId, @RequestParam("image")MultipartFile image) throws IOException {
        //TODO: process PUT request
        ProductDTO updateProduct = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK) ;
    }
}
