package com.freelancers.freelancers_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancers.freelancers_app.dto.StaffUserDTO;
import com.freelancers.freelancers_app.entity.StaffUser;
import com.freelancers.freelancers_app.service.StaffUserService;

@RestController
@RequestMapping("/api/staff")
public class StaffUserController {

    @Autowired
    private StaffUserService staffUserService;

    @PostMapping
    public StaffUser createStaffUser(@RequestBody StaffUserDTO staffUser) {
        return staffUserService.createStaffUser(staffUser);
    }

    @GetMapping
    public List<StaffUser> getAllStaffUsers() {
        return staffUserService.getAllStaffUsers();
    }

    @PutMapping("/{id}")
    public StaffUser updateStaffUser(@PathVariable Long id, @RequestBody StaffUserDTO staffUserDetails) {
        return staffUserService.updateStaffUser(id, staffUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaffUser(@PathVariable Long id) {
        staffUserService.deleteStaffUser(id);
        return ResponseEntity.noContent().build();
    }
}
