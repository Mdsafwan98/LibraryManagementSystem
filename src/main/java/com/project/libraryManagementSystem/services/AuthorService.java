package com.project.libraryManagementSystem.services;

import com.project.libraryManagementSystem.models.Author;
import com.project.libraryManagementSystem.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is used as a service for Author API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    /**
     * Method to create or get author details from database.
     *
     * @param author
     * @return
     */

    public Author getOrCreateAuthor(Author author) {
        Author authorRetrieved = authorRepository.findByAuthorEmail(author.getAuthorEmail());
        if (authorRetrieved == null) {
            authorRepository.save(author);
        }
        return authorRetrieved;
    }
}
