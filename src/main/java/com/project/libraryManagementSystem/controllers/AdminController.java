package com.project.libraryManagementSystem.controllers;

import com.project.libraryManagementSystem.dtos.UpdateAdminRequest;
import com.project.libraryManagementSystem.models.Admin;
import com.project.libraryManagementSystem.dtos.CreateAdminRequest;
import com.project.libraryManagementSystem.services.AdminService;
import com.project.libraryManagementSystem.utils.InputValidation;
import com.project.libraryManagementSystem.utils.SuccessResponse;
import com.project.libraryManagementSystem.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * This class is used as a controller for Admin API.
 *
 * @author safwanmohammed907@gmal.com
 */
@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    private final InputValidation inputDetails;

    public AdminController(InputValidation inputDetails) {
        this.inputDetails = inputDetails;
    }

    /**
     * Method to create new admin details.
     *
     * @param request
     * @param result
     * @return
     * @throws ValidationException
     */
    @PostMapping("/admin")
    public ResponseEntity<String> createAdmin(@RequestBody @Valid CreateAdminRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        boolean admin = adminService.createAdminDetails(request.to());
        if (admin) {
            return ResponseEntity.ok("Admin is created successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to create admin.");
        }
    }

    /**
     * Method to get admin details by id.
     *
     * @param id
     * @return
     * @throws ValidationException
     */
    @GetMapping("/admin/{id}")
    public Admin getAdmin(@PathVariable(required = false) Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Admin id is mandatory.");
        }
        return adminService.getAdminDetails(id);
    }

    /**
     * Method to get all admin details.
     *
     * @return
     */
    @GetMapping("/admins")
    public List<Admin> getAllAdmins() throws ValidationException {
        return adminService.getAllAdmins();
    }

    /**
     * Method to delete existing admin details by id.
     *
     * @param id
     */
    @DeleteMapping("/admin")
    public ResponseEntity<String> deleteAdmin(@RequestParam(required = false) Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("Admin Id is mandatory.");
        }
        boolean deleteAdmin = adminService.deleteAdminDetails(id);
        if (deleteAdmin) {
            return ResponseEntity.ok("Admin is deleted successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete admin.");
        }
    }

    /**
     * Method to update existing admin details.
     *
     * @param request
     * @return
     */
    @PutMapping("/admin")
    public ResponseEntity<SuccessResponse.AdminResponse> updateAdmin(@RequestBody @Valid UpdateAdminRequest request, BindingResult result) throws ValidationException {
        inputDetails.validateInputDetails(result);
        Admin updatedAdmin = adminService.updateAdminDetails(request.adminConversion());
        if (updatedAdmin != null) {
            SuccessResponse.AdminResponse adminResponse = new SuccessResponse.AdminResponse("Admin details updated successfully.", updatedAdmin);
            return ResponseEntity.ok().body(adminResponse);
        } else {
            return ResponseEntity.badRequest().body(new SuccessResponse.AdminResponse("Failed to update admin.", null));
        }

    }
}
