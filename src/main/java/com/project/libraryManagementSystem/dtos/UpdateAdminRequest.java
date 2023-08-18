package com.project.libraryManagementSystem.dtos;

import com.project.libraryManagementSystem.models.Admin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * This class is used as a data transfer object for updating admin details in Admin API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateAdminRequest {
    @NotNull(message = "Admin id is mandatory.")
    private Integer id;
    private String adminName;
    @NotBlank(message = "Admin email is mandatory.")
    private String adminEmail;

    public Admin adminConversion() {
        return Admin.builder()
                .name(this.adminName)
                .email(this.adminEmail)
                .id(this.id)
                .build();
    }
}
