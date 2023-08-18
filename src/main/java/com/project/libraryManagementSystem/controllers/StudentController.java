package com.project.libraryManagementSystem.controllers;

import com.project.libraryManagementSystem.dtos.UpdateStudentRequest;
import com.project.libraryManagementSystem.models.Student;
import com.project.libraryManagementSystem.dtos.CreateStudentRequest;
import com.project.libraryManagementSystem.services.StudentService;
import com.project.libraryManagementSystem.utils.InputValidation;
import com.project.libraryManagementSystem.utils.SuccessResponse;
import com.project.libraryManagementSystem.utils.ValidationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used as a controller for Student API.
 *
 * @author safwanmohammed907@gmal.com
 */
@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    private final InputValidation inputDetails;

    public StudentController(InputValidation inputDetails) {
        this.inputDetails = inputDetails;
    }

    /**
     * Method to create new student details.
     *
     * @param request
     * @param result
     * @throws ValidationException
     */
    @PostMapping("/student")
    public ResponseEntity<String> createStudent(@RequestBody @Valid CreateStudentRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        boolean student = studentService.createStudentDetails(request.to());
        if (student) {
            return ResponseEntity.ok("Student is created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create student.");
        }
    }

    /**
     * Method to get student details by id.
     *
     * @param id
     * @return
     * @throws ValidationException
     */
    @GetMapping("/student/{id}")
    public Student getStudent(@PathVariable(required = false) Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Student id is mandatory.");
        }
        return studentService.getStudentDetails(id);
    }


    /**
     * Method to get all student details.
     *
     * @return
     */
    @GetMapping("/students")
    public List<Student> getAllStudents() throws ValidationException {
        return studentService.getAllStudents();
    }


    /**
     * Method to delete existing student details by Id.
     *
     * @param id
     * @throws ValidationException
     */
    @DeleteMapping("/student")
    public ResponseEntity<String> deleteStudent(@RequestParam(required = false) Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Student id is mandatory.");
        }
        boolean deleteStudent = studentService.deleteStudentDetails(id);
        if (deleteStudent) {
            return ResponseEntity.ok("Student is deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete student.");
        }
    }

    /**
     * Method to update existing student details.
     *
     * @param request
     * @param result
     * @return
     * @throws ValidationException
     */
    @PutMapping("/student")
    public ResponseEntity<SuccessResponse.StudentResponse> updateStudent(@RequestBody @Valid UpdateStudentRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        Student updatedStudent = studentService.updateStudentDetails(request.studentConversion());
        if (updatedStudent != null) {
            SuccessResponse.StudentResponse student = new SuccessResponse.StudentResponse("Student details updated successfully. ", updatedStudent);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.badRequest().body(new SuccessResponse.StudentResponse("Failed to update student.", null));
        }
    }
}
