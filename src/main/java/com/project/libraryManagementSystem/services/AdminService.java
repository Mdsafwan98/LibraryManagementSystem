package com.project.libraryManagementSystem.services;

import com.project.libraryManagementSystem.models.Admin;
import com.project.libraryManagementSystem.repositories.AdminRepository;
import com.project.libraryManagementSystem.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class is used as a service for Admin API.
 *
 * @author safwanmohammed907@gmal.com
 */
@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;


    /**
     * Method to create new admin details in database.
     *
     * @param admin
     * @return
     * @throws ValidationException
     */
    public boolean createAdminDetails(Admin admin) throws ValidationException {
        Admin existingAdmin = adminRepository.findByEmail(admin.getEmail());
        if (existingAdmin != null) {
            throw new ValidationException("Email id already exist.");
        }
        adminRepository.save(admin);
        return true;
    }

    /**
     * Method to get admin details by Id from database.
     *
     * @param id
     * @return
     * @throws ValidationException
     */
    public Admin getAdminDetails(Integer id) throws ValidationException {
        Admin adminDetails = adminRepository.findById(id).orElse(null);
        if (adminDetails == null) {
            throw new ValidationException("The admin details does not exist for the requested id.");
        }
        return adminDetails;
    }

    /**
     * Method to get all admin details from database.
     *
     * @return
     * @throws ValidationException
     */
    public List<Admin> getAllAdmins() throws ValidationException {
        List<Admin> adminDetails = adminRepository.findAll();
        if (adminDetails.isEmpty()) {
            throw new ValidationException("The are no record exist for admin details in database.");
        }
        return adminDetails;
    }

    /**
     * Method to update admin details in database.
     *
     * @param admin
     * @return
     * @throws ValidationException
     */
    public Admin updateAdminDetails(Admin admin) throws ValidationException {
        Optional<Admin> optionalAdmin = adminRepository.findById(admin.getId());
        if (optionalAdmin.isPresent()) {
            //Get admin details to update
            Admin adminDetails = optionalAdmin.get();
            if (admin.getName() != null) {
                adminDetails.setName(admin.getName());
            }
            if (admin.getEmail() != null) {
                adminDetails.setEmail(admin.getEmail());
            }
            return adminRepository.save(adminDetails);
        } else {
            throw new ValidationException("Admin with id " + admin.getId() + " not found.");
        }
    }

    /**
     * Method to delete existing admin details from database.
     *
     * @param id
     * @return
     * @throws ValidationException
     */
    public boolean deleteAdminDetails(Integer id) throws ValidationException {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
        } else {
            throw new ValidationException("The admin details does not exist for the requested id to delete.");
        }
        return true;
    }
}
