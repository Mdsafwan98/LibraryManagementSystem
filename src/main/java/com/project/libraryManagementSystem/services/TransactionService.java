package com.project.libraryManagementSystem.services;

import com.project.libraryManagementSystem.dtos.InitiateTransactionRequest;
import com.project.libraryManagementSystem.models.*;
import com.project.libraryManagementSystem.repositories.TransactionRepository;
import com.project.libraryManagementSystem.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class is used as a service for Transaction API.
 *
 * @author safwanmohammed907@gmail.com
 */
@Service
public class TransactionService {
    @Autowired
    StudentService studentService;
    @Autowired
    BookService bookService;
    @Autowired
    AdminService adminService;
    @Autowired
    TransactionRepository transactionRepository;
    public static final Integer maxBookAllowed = 3;
    public static final Integer duration = 10;

    /**
     * Method to check whether the book transaction is either an issuance or return.
     *
     * @param request
     * @return
     */
    public boolean initiateTransactionRequest(InitiateTransactionRequest request) throws ValidationException {
        if (request.getTransactionType() == TransactionType.ISSUE) {
            issuance(request);
        } else {
            returnBook(request);
        }
        return true;
    }

    /**
     * Method to validate issuance book request.
     *
     * @param request
     * @return
     * @throws ValidationException
     */
    private String issuance(InitiateTransactionRequest request) throws ValidationException {
        //Get student id
        Student student = studentService.getStudentDetails(request.getStudentId());
        //Get admin id
        Admin admin = adminService.getAdminDetails(request.getAdminId());
        //Get book id
        List<Book> bookList = bookService.find("id", String.valueOf(request.getBookId()));
        Book book;
        //Get book list
        if (bookList != null && !bookList.isEmpty()) {
            book = bookList.get(0);
        } else {
            throw new ValidationException("The record does not exist for book details in database.");
        }
        //Check if the book is available
        if (book.getStudent() != null) {
            throw new ValidationException("The book with id " + book.getId() + " is currently unavailable");
        }
        // Check if the student has reached the books limit
        if (student.getBookList().size() >= maxBookAllowed) {
            throw new ValidationException("The student with id " + student.getId() + " has reached the maximum limit.");
        }
        Transaction transaction = null;
        try {
            transaction = Transaction.builder()
                    .txnId(UUID.randomUUID().toString())
                    .admin(admin)
                    .student(student)
                    .book(book)
                    .transactionType(request.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();
            transactionRepository.save(transaction);
            //Set book to the student
            book.setStudent(student);
            //Update book
            bookService.createOrUpdateBook(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.setTransactionStatus(TransactionStatus.FAILURE);
            }
        } finally {
            if (transaction != null) {
                transactionRepository.save(transaction);
            }
        }
        return transaction != null ? transaction.getTxnId() : "Error occurred while processing the transaction, transaction failed.";
    }

    /**
     * Method to validate return book request.
     *
     * @param request
     * @return
     */
    private String returnBook(InitiateTransactionRequest request) throws ValidationException {
        Student student = studentService.getStudentDetails(request.getStudentId());
        Admin admin = adminService.getAdminDetails(request.getAdminId());
        List<Book> bookList = bookService.find("id", String.valueOf(request.getBookId()));
        Book book;
        //Get book list
        if (bookList != null && !bookList.isEmpty()) {
            book = bookList.get(0);
        } else {
            throw new ValidationException("The record does not exist for book details in database.");
        }
        // Check if book is available
        if (book.getStudent() == null) {
            throw new ValidationException("The book is not assigned to any student.");
        }
        // Check if book is assigned to requested student
        if (!book.getStudent().getId().equals(student.getId())) {
            throw new ValidationException("The book is not assign to your id.");
        }
        Transaction issueTransaction = transactionRepository.findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(student, book, TransactionType.ISSUE);
        if (issueTransaction == null) {
            throw new ValidationException("The record does not exist for the books issued by the student." );
        }
        Transaction transaction = null;
        try {
            Integer fine = calculateFine(issueTransaction.getCreatedOn());
            transaction = Transaction.builder()
                    .txnId(UUID.randomUUID().toString())
                    .book(book)
                    .admin(admin)
                    .student(student)
                    .transactionType(request.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .fine(fine)
                    .build();
            transactionRepository.save(transaction);
            if (fine == 0) {
                book.setStudent(null);
                bookService.createOrUpdateBook(book);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.setTransactionStatus(TransactionStatus.FAILURE);
            }
        } finally {
            if (transaction != null) {
                transactionRepository.save(transaction);
            }
        }
        return transaction != null ? transaction.getTxnId() : "Error occurred while processing the transaction, transaction failed.";
    }

    /**
     * Method to calculate fine for the transaction.
     *
     * @param issuanceDate
     * @return
     */

    private Integer calculateFine(Date issuanceDate) {
        long issueTime = issuanceDate.getTime();
        long currentTime = System.currentTimeMillis();
        long difference = currentTime - issueTime;
        long daysPassed = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        if (daysPassed > duration) {
            return (int) (daysPassed - duration);
        }
        return 0;
    }

    /**
     * Method to validate fine for the transaction.
     *
     * @param amount
     * @param studentId
     * @param txnId
     */
    public boolean payFine(Integer amount, Integer studentId, String txnId) throws ValidationException {
        Transaction returnTransaction = transactionRepository.findByTxnId(txnId);
        if (returnTransaction == null) {
            throw new ValidationException("Transaction id is invalid.");
        }
        Book book = returnTransaction.getBook();
        //Check if the requested amount is matching with the amount in database
        if (!returnTransaction.getFine().equals(amount)) {
            throw new ValidationException("The amount entered is invalid.");
        }
        //Check if the requested student id matching with transaction id
        if (!book.getStudent().getId().equals(studentId)) {
            throw new ValidationException("Student id is invalid for the transaction id.");
        }
        //Check if transaction is matching with the requested student id
        if (!returnTransaction.getTxnId().equals(txnId)) {
            throw new ValidationException("Transaction id is invalid for the student id.");
        }
        returnTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
        book.setStudent(null);
        bookService.createOrUpdateBook(book);
        transactionRepository.save(returnTransaction);
        return true;
    }

}
