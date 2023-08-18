package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.TransactionType;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * This class is used as a data transfer object for initiating transaction request for Transaction API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitiateTransactionRequest {
    @NotNull(message = "Student id is mandatory.")
    private Integer studentId;
    @NotNull(message = "Book id is mandatory.")
    private Integer bookId;
    @NotNull(message = "Admin id is mandatory.")
    private Integer adminId;
    private TransactionType transactionType;




}
