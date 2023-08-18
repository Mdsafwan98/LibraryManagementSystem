package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.Author;
import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.models.Genre;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * This class is used as a data transfer object for updating book details in Book API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookRequest {
    @NotNull(message = "Book id is mandatory.")
    private Integer id;
    @NotBlank(message = "Book name is mandatory.")
    private String bookName;
    private Genre genre;
    private String authorName;
    @NotBlank(message = "Author email is mandatory.")
    private String authorEmail;

    public Book bookConversion() {
        Author authorDetails = Author.builder()
                .authorName(this.authorName)
                .authorEmail(this.authorEmail)
                .build();
        return Book.builder()
                .id(this.id)
                .bookName(this.bookName)
                .genre(this.genre)
                .author(authorDetails)
                .build();
    }
}
