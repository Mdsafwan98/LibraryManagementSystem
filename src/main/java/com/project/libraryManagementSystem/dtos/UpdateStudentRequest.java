package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.Student;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * This class is used as a data transfer object for updating student details in Student API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStudentRequest {
    @NotNull(message = "Student id is mandatory.")
    private Integer id;
    private String studentName;
    @NotBlank(message = "Student email is mandatory.")
    private String studentEmail;
    private Integer age;

    public Student studentConversion() {
        return Student.builder()
                .id(this.id)
                .name(this.studentName)
                .age(this.age)
                .email(this.studentEmail)
                .build();
    }
}
