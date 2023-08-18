package com.project.libraryManagementSystem.controllers;

import com.project.libraryManagementSystem.dtos.UpdateBookRequest;
import com.project.libraryManagementSystem.utils.SuccessResponse;
import com.project.libraryManagementSystem.utils.ValidationException;
import com.project.libraryManagementSystem.dtos.CreateBookRequest;
import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.services.BookService;
import com.project.libraryManagementSystem.utils.InputValidation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used as a controller for Book API.
 *
 * @author safwanmohammed907@gmal.com
 */
@RestController
public class BookController {
    @Autowired
    BookService bookService;
    private final InputValidation inputDetails;

    public BookController(InputValidation inputDetails) {
        this.inputDetails = inputDetails;
    }


    /**
     * Method to create new book details.
     *
     * @param request
     * @param result
     * @throws ValidationException
     */
    @PostMapping("/book")
    public ResponseEntity<String> create(@RequestBody @Valid CreateBookRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        boolean book = bookService.createOrUpdateBook(request.to());
        if (book) {
            return ResponseEntity.ok("Student is created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create student.");
        }
    }

    /**
     * Method to get book details by search key and search value.
     *
     * @param searchId
     * @param searchValue
     * @return
     * @throws ValidationException
     */
    @GetMapping("/book")
    public List<Book> getBooks(@RequestParam("searchId") String searchId, @RequestParam("searchValue") String searchValue) throws ValidationException {
        if (searchId == null || searchId.isBlank()) {
            throw new ValidationException("search id is mandatory.");
        }
        if (searchValue == null || searchValue.isBlank()) {
            throw new ValidationException("search value is mandatory.");
        }
        return bookService.find(searchId, searchValue);
    }

    /**
     * Method to update existing book details.
     *
     * @param request
     * @param result
     * @return
     * @throws ValidationException
     */
    @PutMapping("/book")
    public ResponseEntity<SuccessResponse.BookResponse> updateBook(@RequestBody @Valid UpdateBookRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        Book updatedBook = bookService.updateBookDetails(request.bookConversion());
        if (updatedBook != null) {
            SuccessResponse.BookResponse bookResponse = new SuccessResponse.BookResponse("Book details updated successfully.", updatedBook);
            return ResponseEntity.ok().body(bookResponse);
        } else {
            return ResponseEntity.badRequest().body(new SuccessResponse.BookResponse("Failed to update book.", null));
        }
    }

    /**
     * Method to delete existing book by id.
     *
     * @param id
     * @throws ValidationException
     */
    @DeleteMapping("/book")
    public ResponseEntity<String> deleteBook(@RequestParam(required = false) Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Id is mandatory.");
        }
        boolean deleteBook = bookService.deleteBookDetails(id);
        if (deleteBook) {
            return ResponseEntity.ok("Book is deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete book.");
        }
    }
}

