package com.obss.bookWeb.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.obss.bookWeb.model.FavList;
import com.obss.bookWeb.service.FavListService;

@RestController
@RequestMapping("/bookweb/favlist")
@RequiredArgsConstructor
public class FavListController {

    private final FavListService favListService;

    @PostMapping("/add-product")
    public ResponseEntity<FavList> addProductToCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        FavList favList = favListService.addProductToFavlist(userId, productId);
        return new ResponseEntity<>(favList, HttpStatus.CREATED);

    }

    @DeleteMapping("/remove-product/{favlistId}/{productId}")
    public ResponseEntity<FavList> removeProductFromCart(@PathVariable Integer favlistId, @PathVariable Integer productId) {
        favListService.removeProductFromFavlist(favlistId, productId);
        FavList updatedFavList = favListService.getAllFavlistProduct(favlistId); // Fetch the updated cart data
        return ResponseEntity.ok(updatedFavList); // Return the updated cart data
    }

    @DeleteMapping("/empty-favlist/{favlistId}")
    public ResponseEntity<FavList> removeAllProductFromCart(@PathVariable Integer favlistId) {
        favListService.removeAllProductFromFavlist(favlistId);
        FavList updatedFavList = favListService.getAllFavlistProduct(favlistId); // Fetch the updated cart data
        return ResponseEntity.ok(updatedFavList); // Return the updated cart data
    }

    @GetMapping("/products/{favlistId}")
    public ResponseEntity<FavList> getAllCartProducts(@PathVariable Integer favlistId) {
        System.out.println("---------------------->here we are in cart: ");

        FavList products = favListService.getAllFavlistProduct(favlistId);
        System.out.println("---------------------->we passed the cart: ");

        return ResponseEntity.ok(products);
    }
}

