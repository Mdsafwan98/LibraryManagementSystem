package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.Admin;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * This class is used as a data transfer object for Admin API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminRequest {
    @NotBlank(message = "Admin name is mandatory.")
    private String adminName;
    @NotBlank(message = "Email name is mandatory.")
    private String adminEmail;

    public Admin to() {
        return Admin.builder()
                .name(this.adminName)
                .email(this.adminEmail)
                .build();
    }
}
