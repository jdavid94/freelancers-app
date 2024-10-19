package com.freelancers.freelancers_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancers.freelancers_app.dto.StaffUserDTO;
import com.freelancers.freelancers_app.entity.StaffUser;
import com.freelancers.freelancers_app.exception.ResourceNotFoundException;
import com.freelancers.freelancers_app.repository.StaffUserRepository;

@Service
public class StaffUserService {
    
    @Autowired
    private StaffUserRepository staffUserRepository;

    private static final String STAFF_NOT_FOUND = "Staff user not found";

    public StaffUser createStaffUser(StaffUserDTO staffUserDTO) {
        StaffUser staffUser = new StaffUser();
        staffUser.setFirstName(staffUserDTO.getFirstName());
        staffUser.setLastName(staffUserDTO.getLastName());
        return staffUserRepository.save(staffUser);
    }

    public List<StaffUser> getAllStaffUsers() {
        return staffUserRepository.findAll();
    }

    public StaffUser getStaffUserById(Long id) {
        return staffUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STAFF_NOT_FOUND));
    }

    public StaffUser updateStaffUser(Long id, StaffUserDTO staffUserDetails) {
        StaffUser staffUser = staffUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STAFF_NOT_FOUND));

        staffUser.setFirstName(staffUserDetails.getFirstName());
        staffUser.setLastName(staffUserDetails.getLastName());
        return staffUserRepository.save(staffUser);
    }

    public void deleteStaffUser(Long id) {
        StaffUser staffUser = staffUserRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(STAFF_NOT_FOUND));
        staffUserRepository.delete(staffUser);
    }

}
