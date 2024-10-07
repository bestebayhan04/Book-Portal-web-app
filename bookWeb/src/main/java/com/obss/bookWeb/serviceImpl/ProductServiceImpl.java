package com.obss.bookWeb.serviceImpl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.obss.bookWeb.model.Author;
import com.obss.bookWeb.model.User;
import com.obss.bookWeb.repository.AuthorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.obss.bookWeb.exception.ProductException;
import com.obss.bookWeb.model.Product;
import com.obss.bookWeb.modelDto.ProductDto;
import com.obss.bookWeb.repository.ProductRepo;
import com.obss.bookWeb.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepository;
    private final AuthorRepo authorRepository;


    @Override
    public Product addProduct(ProductDto productDTO) throws ProductException {
        if (productDTO == null) {
            throw new ProductException("Product DTO cannot be null");
        }

        Author author = new Author();
        author.setName(productDTO.getAuthorname());
        author = authorRepository.save(author);

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setImageUrl(productDTO.getImageUrl());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setAuthor(author);


        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Integer productId, ProductDto updatedProduct) throws ProductException {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ProductException("Product with ID " + productId + " not found.");
        }
        Product existingProduct = productOptional.get();

        // Update the existing product's properties with the new data
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setImageUrl(updatedProduct.getImageUrl());
        existingProduct.setDescription(updatedProduct.getDescription());

        // Save the updated product
        return productRepository.save(existingProduct);
    }


    @Override
    public List<Product> getProductByName(String name) throws ProductException {

        List<Product> existProductByName = productRepository.findByName(name);
        if (existProductByName.isEmpty()) {
            throw new ProductException("Product Not found with name " + name);
        }
        return existProductByName;
    }

    @Override
    public List<Product> getAllProduct(String keyword) throws ProductException {

        List<Product> products;

        if (keyword != null) {
            products = productRepository.findAllByNameContainingIgnoreCase(keyword);
        } else {
            products = productRepository.findAll();
        }
        if (products.isEmpty()) {
            throw new ProductException("Product List Empty");
        }

        return products;
    }

    @Override
    public List<Product> getProductByCategory(String category) throws ProductException {
        // Retrieve products by category from the database
        List<Product> allproductCategoryName = productRepository.getProductCategoryName(category);
        if (allproductCategoryName.isEmpty())
            throw new ProductException("Product with category Name " + category + " not found.");

        return allproductCategoryName;
    }

    @Override
    public void removeProduct(Integer productId) throws ProductException {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product with ID " + productId + " not found."));

        productRepository.delete(existingProduct);
    }

    @Override
    public Product getSingleProduct(Integer productId) {

        Product single = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("Product not found"));
        return single;
    }
}