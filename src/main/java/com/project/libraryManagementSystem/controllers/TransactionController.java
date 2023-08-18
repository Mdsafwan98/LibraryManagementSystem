package com.project.libraryManagementSystem.controllers;

import com.project.libraryManagementSystem.dtos.InitiateTransactionRequest;
import com.project.libraryManagementSystem.dtos.initiatePaymentRequest;
import com.project.libraryManagementSystem.services.TransactionService;
import com.project.libraryManagementSystem.utils.InputValidation;
import com.project.libraryManagementSystem.utils.ValidationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used as a controller for Transaction API.
 *
 * @author safwanmohammed907@gmal.com
 */
@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    private final InputValidation inputDetails;

    public TransactionController(InputValidation inputDetails) {
        this.inputDetails = inputDetails;
    }

    /**
     * Method to initiate transaction request.
     *
     * @param request
     * @param result
     * @return
     * @throws ValidationException
     */
    @PostMapping("/transaction")
    public ResponseEntity<String> initiateTransaction(@RequestBody @Valid InitiateTransactionRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        boolean initiateTxn = transactionService.initiateTransactionRequest(request);
        if (initiateTxn) {
            return ResponseEntity.ok("Transaction is successful.");
        } else {
            return ResponseEntity.badRequest().body("Transaction failed.");
        }
    }

    /**
     * Method to initiate payment.
     *
     * @param request
     * @param result
     * @return
     * @throws ValidationException
     */

    @PostMapping("/transaction/payment")
    public ResponseEntity<String> makePayment(@RequestBody @Valid initiatePaymentRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        boolean paymentSuccess = transactionService.payFine(request.getAmount(), request.getStudentId(), request.getTransactionId());
        if (paymentSuccess) {
            return ResponseEntity.ok("Payment is successful.");
        } else {
            return ResponseEntity.badRequest().body("Payment failed.");
        }
    }
}
