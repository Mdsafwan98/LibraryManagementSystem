package com.project.libraryManagementSystem.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

/**
 * This class handles validation of input details using BindingResult class.
 */
public class InputValidation {
    public void validateInputDetails(BindingResult result) throws ValidationException {
        if (result.hasErrors()) {
            // Handle validation errors
            String errorMessages = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessages);
        }

    }
}
