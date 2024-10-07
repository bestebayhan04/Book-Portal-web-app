package com.obss.bookWeb.controller;


import com.obss.bookWeb.model.Author;
import com.obss.bookWeb.modelDto.AuthorDto;
import com.obss.bookWeb.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookweb/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<Author> addSeller(@RequestBody AuthorDto authorDto) {
        Author newAuthor = authorService.addAuthor(authorDto);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    @PutMapping("/update/{authorId}")
    public ResponseEntity<Author> updateSeller(@PathVariable Integer authorId, @RequestBody AuthorDto authorDto) {
        Author updatedAuthor = authorService.updateAuthor(authorId, authorDto);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{authorId}")
    public ResponseEntity<String> deleteSeller(@PathVariable Integer authorId) {
        authorService.removeAuthor(authorId);
        return new ResponseEntity<>("Author removed successfully.", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllSellers() {
        List<Author> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> getSellerById(@PathVariable Integer authorId) {
        Author author = authorService.getAuthorById(authorId);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }
}
