package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.Author;
import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.models.Genre;
import javax.validation.constraints.NotBlank;
import lombok.*;

/**
 * This class is used as a data transfer object for Book API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {
    @NotBlank(message = "Book name is mandatory.")
    private String bookName;
    private Genre genre;
    @NotBlank(message = "Author name is mandatory.")
    private String authorName;
    @NotBlank(message = "Author email is mandatory.")
    private String authorEmail;

    public Book to() {
        Author authorDetails = Author.builder()
                .authorName(this.authorName)
                .authorEmail(this.authorEmail)
                .build();
        return Book.builder()
                .bookName(this.bookName)
                .genre(this.genre)
                .author(authorDetails)
                .build();

    }
}


