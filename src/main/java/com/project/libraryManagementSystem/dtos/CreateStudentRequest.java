package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.Student;
import javax.validation.constraints.NotBlank;
import lombok.*;

/**
 * This class is used as a data transfer object for Student API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {
    @NotBlank(message = "Student name is mandatory.")
    private String studentName;
    @NotBlank(message = "Student email is mandatory.")
    private String studentEmail;
    private Integer age;

    public Student to() {
        return Student.builder()
                .name(this.studentName)
                .age(this.age)
                .email(this.studentEmail)
                .build();
    }
}
