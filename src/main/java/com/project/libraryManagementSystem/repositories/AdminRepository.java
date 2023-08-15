package com.project.libraryManagementSystem.repositories;

import com.project.libraryManagementSystem.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This class is used as a repository for Admin API.
 *
 * @author safwanmohammed907@gmal.com
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
}
