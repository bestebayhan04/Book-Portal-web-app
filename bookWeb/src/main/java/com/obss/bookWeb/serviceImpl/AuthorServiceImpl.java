package com.obss.bookWeb.serviceImpl;


import com.obss.bookWeb.exception.ProductException;
import com.obss.bookWeb.model.Product;
import com.obss.bookWeb.model.Author;
import com.obss.bookWeb.modelDto.AuthorDto;
import com.obss.bookWeb.repository.AuthorRepo;
import com.obss.bookWeb.exception.AuthorException;
import com.obss.bookWeb.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepo authorRepository;

    @Override
    public Author addAuthor(AuthorDto authorDto) throws AuthorException {
        Author author = new Author();
        author.setName(authorDto.getName());
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Integer authorId, AuthorDto authorDto) throws AuthorException {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorException("Seller not found"));
        author.setName(authorDto.getName());
        return authorRepository.save(author);
    }

    @Override
    public void removeAuthor(Integer authorId) throws AuthorException {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorException("Seller not found"));
        authorRepository.delete(author);
    }

    @Override
    public List<Author> getAllAuthors() {

        List<Author> authors;

        authors= authorRepository.findAll();

        if(authors.isEmpty()){
            throw new AuthorException("seller List Empty");
        }

        return authors;
    }

    @Override
    public Author getAuthorById(Integer authorId) throws AuthorException {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new AuthorException("Seller not found"));
    }
}

