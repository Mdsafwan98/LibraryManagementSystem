package com.project.libraryManagementSystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * This class is used as a data transfer object for payment request for Transaction API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class initiatePaymentRequest {
    @NotNull(message = "Student id is mandatory.")
    private Integer studentId;
    @NotNull(message = "Amount is mandatory.")
    private Integer amount;
    @NotBlank(message = "Transaction id is mandatory.")
    private String transactionId;
}
