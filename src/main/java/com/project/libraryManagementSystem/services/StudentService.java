package com.project.libraryManagementSystem.services;

import com.project.libraryManagementSystem.models.Student;
import com.project.libraryManagementSystem.repositories.StudentRepository;
import com.project.libraryManagementSystem.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * This class is used as a service for Student API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    /**
     * Method to create student details in database.
     *
     * @param student
     * @return
     * @throws ValidationException
     */
    public boolean createStudentDetails(Student student) throws ValidationException {
        Student existingStudent = studentRepository.findByEmail(student.getEmail());
        if (existingStudent != null) {
            throw new ValidationException("Email id already exist.");
        }
        studentRepository.save(student);
        return true;
    }

    /**
     * Method to get student details by id from database.
     *
     * @param id
     * @return
     * @throws ValidationException
     */
    public Student getStudentDetails(Integer id) throws ValidationException {
        Student studentDetails = studentRepository.findById(id).orElse(null);
        if (studentDetails == null) {
            throw new ValidationException("The student record does not exist for the requested id.");
        }
        return studentDetails;
    }

    /**
     * Method to get all student details from database.
     *
     * @return
     */
    public List<Student> getAllStudents() throws ValidationException {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ValidationException("The are no record exist for student details in database.");
        }
        return students;
    }

    /**
     * Method to update student details in database.
     *
     * @param student
     * @return
     * @throws ValidationException
     */
    public Student updateStudentDetails(Student student) throws ValidationException {
        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        if (optionalStudent.isPresent()) {
            //Get student details to update
            Student studentDetails = optionalStudent.get();
            if (student.getName() != null) {
                studentDetails.setName(student.getName());
            }
            if (student.getAge() != null) {
                studentDetails.setEmail(student.getEmail());
            }
            return studentRepository.save(studentDetails);
        } else {
            throw new ValidationException("Student with id " + student.getId() + " not found.");
        }
    }


    /**
     * Method to delete student details from database.
     *
     * @param id
     * @throws ValidationException
     */
    public boolean deleteStudentDetails(Integer id) throws ValidationException {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new ValidationException("The student details does not exist for the requested id to delete..");
        }
        return true;
    }
}



