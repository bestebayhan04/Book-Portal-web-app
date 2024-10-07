package com.obss.bookWeb.service;

import com.obss.bookWeb.model.Author;
import com.obss.bookWeb.modelDto.AuthorDto;
import com.obss.bookWeb.exception.AuthorException;

import java.util.List;

public interface AuthorService {

    Author addAuthor(AuthorDto authorDto) throws AuthorException;

    Author updateAuthor(Integer authorId, AuthorDto authorDto) throws AuthorException;

    void removeAuthor(Integer authorId) throws AuthorException;

    List<Author> getAllAuthors();

    Author getAuthorById(Integer authorId) throws AuthorException;
}
