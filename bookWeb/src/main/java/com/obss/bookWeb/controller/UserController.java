package com.obss.bookWeb.controller;

import java.util.List;

import com.obss.bookWeb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.obss.bookWeb.model.User;
import com.obss.bookWeb.modelDto.UserDto;
import com.obss.bookWeb.modelDto.UserUpdateDto;  // Import the new DTO
import com.obss.bookWeb.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookweb/customers")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> addUser(@Valid @RequestBody UserDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteUser(@PathVariable("customerId") Integer customerId) {
        String message = userService.deleteUser(customerId);
        return ResponseEntity.ok(message);}


    @GetMapping("/{customerid}")
    public ResponseEntity<User> getUserDetails(@PathVariable("customerid") Integer customerId) {
        User user = userService.getUserDetails(customerId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get-all-customer")
    public ResponseEntity<List<User>> getAllUserDetails() {
        List<User> users = userService.getAllUserDetails();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{customerId}")
    public ResponseEntity<User> updateUserDetails(@PathVariable("customerId") Integer customerId,
                                                  @Valid @RequestBody UserUpdateDto userUpdateDTO) {
        User updatedUser = userService.updateUserDetails(customerId, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
