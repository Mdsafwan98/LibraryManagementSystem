package com.project.libraryManagementSystem.repositories;

import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * This class is used as a repository for Book API.
 *
 * @author safwanmohammed907@gmal.com
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookName(String bookName);

    List<Book> findByGenre(Genre genre);
   // Retreiving author details from author in book ca be done in 2 ways below.
   /* @Query("select b from Book b, Author a where b.author.id = a.id and a.authorName = ?1")

    List<Book> findByAuthor(String authorName);*/

    List<Book> findByAuthor_AuthorName(String authorName);
}
