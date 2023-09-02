package com.project.libraryManagementSystem.services;

import com.project.libraryManagementSystem.models.*;
import com.project.libraryManagementSystem.repositories.AuthorRepository;
import com.project.libraryManagementSystem.repositories.BookRepository;
import com.project.libraryManagementSystem.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class is used as a service for Book API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    AuthorService authorService;

    /**
     * Method to create new book details in database.
     *
     * @param book
     */

    public boolean createOrUpdateBook(Book book) {
        Author authorDetails = authorService.getOrCreateAuthor(book.getAuthor());
        book.setAuthor(authorDetails);
        bookRepository.save(book);
        return true;
    }

    /**
     * Method to get book details by search key and search value.
     *
     * @param searchId
     * @param searchValue
     * @return
     * @throws ValidationException
     */
    public List<Book> find(String searchId, String searchValue) throws ValidationException {
        switch (searchId) {
            case "id" -> {
                Optional<Book> bookDetails = bookRepository.findById(Integer.parseInt(searchValue));
                if (bookDetails.isPresent()) {
                    return List.of(bookDetails.get());
                } else {
                    throw new ValidationException("Book id is invalid");
                }
            }
            case "genre" -> {
                return bookRepository.findByGenre(Genre.valueOf(searchValue));
            }
            case "authorName" -> {
                List<Book> authorValue = bookRepository.findByAuthor_AuthorName(searchValue);
                boolean isAuthor = authorValue.stream().anyMatch(a -> a.getAuthor().getAuthorName().equals(searchValue));
                if (!isAuthor) {
                    throw new ValidationException("No author found with the name: " + searchValue);
                }
                //Setting transaction list to null as the transaction details should not come while retrieving book details.
                authorValue.forEach(book -> book.setTransactionList(null));
                return authorValue;
            }
            case "bookName" -> {
                List<Book> bookValue = bookRepository.findByBookName(searchValue);
                boolean isBook = bookValue.stream().anyMatch(a -> a.getBookName().equals(searchValue));
                if (!isBook) {
                    throw new ValidationException("No book found with the name: " + searchValue);
                }
                return bookValue;
            }
            default -> throw new ValidationException("Search Id " + searchId + " is invalid");
        }
    }

    /**
     * Method to update book details in database.
     *
     * @param book
     * @return
     * @throws ValidationException
     */
    public Book updateBookDetails(Book book) throws ValidationException {
        Optional<Book> optionalBook = bookRepository.findById(book.getId());
        if (optionalBook.isPresent()) {
            //Get book details to update
            Book bookDetails = optionalBook.get();
            if (book.getBookName() != null) {
                bookDetails.setBookName(book.getBookName());
            }
            if (book.getGenre() != null) {
                bookDetails.setGenre(book.getGenre());
            }
            Author authorDetails = bookDetails.getAuthor();
            //if author is not available, create a new author
            if (authorDetails == null) {
                authorDetails = new Author();
                Author author = authorRepository.save(authorDetails);
                bookDetails.setAuthor(author);
            }
            if (book.getAuthor().getAuthorName() != null) {
                authorDetails.setAuthorName(book.getAuthor().getAuthorName());
            }
            if (book.getAuthor().getAuthorEmail() != null) {
                authorDetails.setAuthorEmail(book.getAuthor().getAuthorEmail());
            }
            return bookRepository.save(bookDetails);
        } else {
            throw new ValidationException("Book with ID " + book.getId() + " not found.");
        }
    }

    /**
     * Method to delete book details by id.
     *
     * @param id
     * @return
     * @throws ValidationException
     */
    public boolean deleteBookDetails(Integer id) throws ValidationException {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new ValidationException("The record does not exist for the requested id to delete.");
        }
        return true;
    }
}

