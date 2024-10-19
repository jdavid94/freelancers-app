package com.freelancers.freelancers_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancers.freelancers_app.dto.FreelancerDTO;
import com.freelancers.freelancers_app.entity.Freelancer;
import com.freelancers.freelancers_app.exception.ResourceNotFoundException;
import com.freelancers.freelancers_app.repository.FreelancerRepository;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository freelancerRepository; 

    private static final String FREELANCER_NOT_FOUND = "Freelancer not found";

    public Freelancer registerFreelancer(FreelancerDTO freelancerDTO) {
        Freelancer freelancer = new Freelancer();
        freelancer.setFirstName(freelancerDTO.getFirstName());
        freelancer.setLastName(freelancerDTO.getLastName());
        freelancer.setDateOfBirth(freelancerDTO.getDateOfBirth());
        freelancer.setGender(freelancerDTO.getGender());
        freelancer.setStatus(Freelancer.Status.NEW_FREELANCER); 
        return freelancerRepository.save(freelancer);
    }

     public List<Freelancer> getAllFreelancers() {
        return freelancerRepository.findAll();
    }

    public Freelancer updateFreelancer(Long id, FreelancerDTO freelancerDetails) {
        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FREELANCER_NOT_FOUND));

        freelancer.setFirstName(freelancerDetails.getFirstName());
        freelancer.setLastName(freelancerDetails.getLastName());
        freelancer.setDateOfBirth(freelancerDetails.getDateOfBirth());
        freelancer.setGender(freelancerDetails.getGender());
        return freelancerRepository.save(freelancer);
    }

    public void markFreelancerDeleted(Long id) {
        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FREELANCER_NOT_FOUND));

        freelancer.setStatus(Freelancer.Status.DELETED);
        freelancer.setDeletedDate(LocalDate.now());  // To save delete date
        freelancerRepository.save(freelancer);
    }

    /*
     * To Do
     * Get freelancers deleted in the last 7 days
     * Get new freelancers who need to be approved
     * Approve a freelancer (change status to VERIFIED)
    */

}
