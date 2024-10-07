package com.obss.bookWeb.service;


import java.util.List;

import com.obss.bookWeb.exception.ProductException;
import com.obss.bookWeb.model.Product;
import com.obss.bookWeb.model.User;
import com.obss.bookWeb.modelDto.ProductDto;

public interface ProductService {

    Product addProduct(ProductDto productDTO) throws ProductException;

    public Product updateProduct(Integer productId,ProductDto product)throws ProductException;

    public List<Product> getProductByName(String name)throws ProductException;

    List<Product> getAllProduct(String keyword) throws ProductException;

    public List<Product> getProductByCategory(String category) throws ProductException;

    public void removeProduct(Integer productId)throws ProductException;

    public Product getSingleProduct(Integer productId);
}
