package com.obss.bookWeb.controller;


import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.obss.bookWeb.model.Product;
import com.obss.bookWeb.modelDto.ProductDto;
import com.obss.bookWeb.service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookweb/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductDto productDTO) {
        Product newProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct( @PathVariable Integer productId, @Valid @RequestBody ProductDto updatedProduct) {
        Product updatedProductResult = productService.updateProduct(productId, updatedProduct);
        return new ResponseEntity<>(updatedProductResult, HttpStatus.OK);
    }

    @GetMapping("/product-By-name/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        List<Product> products = productService.getProductByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> search(
            @RequestParam(required = false) String keyword
    ) {
        List<Product> products = productService.getAllProduct(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable Integer productId) {
        Product singleProduct = productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Integer productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>("Product removed successfully.", HttpStatus.OK);
    }
}

