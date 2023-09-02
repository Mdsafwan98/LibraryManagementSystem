package com.project.libraryManagementSystem.repositories;

import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * This class is used as a repository for Book API.
 *
 * @author safwanmohammed907@gmal.com
 */
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookName(String bookName);
    List<Book> findByGenre(Genre genre);
    List<Book> findByAuthor_AuthorName(String authorName);
}
