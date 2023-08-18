package com.project.libraryManagementSystem.dtos;

import javax.validation.constraints.NotBlank;
import lombok.*;

/**
 * This class is used as a data transfer object for Book API for retrieving book
 * details.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchBookRequest {
    @NotBlank(message = "Search id is mandatory.")
    private String searchId;
    @NotBlank(message = "Search value is mandatory.")
    private String searchValue;

}
