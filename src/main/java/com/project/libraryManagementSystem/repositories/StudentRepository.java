package com.project.libraryManagementSystem.repositories;

import com.project.libraryManagementSystem.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is used as a repository for Student API.
 *
 * @author safwanmohammed907@gmal.com
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByEmail(String email);
}
