package com.obss.bookWeb.controller;


import com.obss.bookWeb.model.ReadList;
import com.obss.bookWeb.service.ReadListService;
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



@RestController
@RequestMapping("/bookweb/readlist")
@RequiredArgsConstructor
public class ReadListController {

    private final ReadListService readListService;

    @PostMapping("/add-product")
    public ResponseEntity<ReadList> addProductToCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        ReadList readList = readListService.addProductToReadlist(userId, productId);
        return new ResponseEntity<>(readList, HttpStatus.CREATED);

    }

    @DeleteMapping("/remove-product/{readlistId}/{productId}")
    public ResponseEntity<ReadList> removeProductFromCart(@PathVariable Integer readlistId, @PathVariable Integer productId) {
        readListService.removeProductFromReadlist(readlistId, productId);
        ReadList updatedReadList = readListService.getAllReadlistProduct(readlistId); // Fetch the updated cart data
        return ResponseEntity.ok(updatedReadList); // Return the updated cart data
    }

    @DeleteMapping("/empty-readlist/{readlistId}")
    public ResponseEntity<ReadList> removeAllProductFromCart(@PathVariable Integer readlistId) {
        readListService.removeAllProductFromReadlist(readlistId);
        ReadList updatedReadList = readListService.getAllReadlistProduct(readlistId); // Fetch the updated cart data
        return ResponseEntity.ok(updatedReadList); // Return the updated cart data
    }

    @GetMapping("/products/{readlistId}")
    public ResponseEntity<ReadList> getAllCartProducts(@PathVariable Integer readlistId) {
        System.out.println("---------------------->here we are in cart: ");

        ReadList products = readListService.getAllReadlistProduct(readlistId);
        System.out.println("---------------------->we passed the cart: ");

        return ResponseEntity.ok(products);
    }
}

