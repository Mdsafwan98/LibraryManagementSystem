package com.project.libraryManagementSystem.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.libraryManagementSystem.models.Admin;
import com.project.libraryManagementSystem.models.Book;
import com.project.libraryManagementSystem.models.Student;

/**
 * This class is used to be designed for generating structured success responses after updating the details.
 */

public class SuccessResponse {
    public static class AdminResponse {
        @JsonProperty("message")
        private String message;
        @JsonProperty("admin")
        private Admin admin;

        public AdminResponse(String message, Admin admin) {
            this.message = message;
            this.admin = admin;
        }
    }

    public static class StudentResponse {
        @JsonProperty("message")
        private String message;
        @JsonProperty("student")
        private Student student;

        public StudentResponse(String message, Student student) {
            this.message = message;
            this.student = student;
        }
    }

    public static class BookResponse {
        @JsonProperty("message")
        private String message;
        @JsonProperty("book")
        private Book book;

        public BookResponse(String message, Book book) {
            this.message = message;
            this.book = book;
        }
    }
}
