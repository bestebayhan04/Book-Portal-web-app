package com.obss.bookWeb.repository;

import com.obss.bookWeb.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);
}