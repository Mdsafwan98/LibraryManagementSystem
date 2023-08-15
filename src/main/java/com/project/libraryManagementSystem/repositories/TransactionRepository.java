package com.project.libraryManagementSystem.repositories;

import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.models.Student;
import com.project.libraryManagementSystem.models.Transaction;
import com.project.libraryManagementSystem.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is used as a repository for Transaction API.
 *
 * @author safwanmohammed907@gmal.com
 */
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);

    Transaction findByTxnId(String txnId);

}
