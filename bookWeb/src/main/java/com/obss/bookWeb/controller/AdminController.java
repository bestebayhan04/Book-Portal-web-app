package com.obss.bookWeb.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.obss.bookWeb.model.User;
import com.obss.bookWeb.modelDto.AdminDto;
import com.obss.bookWeb.modelDto.UserDto;
import com.obss.bookWeb.service.UserService;

@RestController
@RequestMapping("/bookweb/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody AdminDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User addedUser = userService.addUserAdmin(user);
        return ResponseEntity.ok(addedUser);
    }

}