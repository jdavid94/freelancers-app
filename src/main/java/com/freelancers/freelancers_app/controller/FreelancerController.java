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

import com.freelancers.freelancers_app.dto.FreelancerDTO;
import com.freelancers.freelancers_app.entity.Freelancer;
import com.freelancers.freelancers_app.service.FreelancerService;

@RestController
@RequestMapping("/api/freelancers")
public class FreelancerController {

    @Autowired
    private FreelancerService freelancerService;

    @GetMapping
    public List<Freelancer> getAllFreelancers() {
        return freelancerService.getAllFreelancers();
    }

    @PostMapping
    public Freelancer registerFreelancer(@RequestBody FreelancerDTO freelancer) {
        return freelancerService.registerFreelancer(freelancer);
    }

    @PutMapping("/{id}")
    public Freelancer updateFreelancer(@PathVariable Long id, @RequestBody FreelancerDTO freelancerDetails) {
        return freelancerService.updateFreelancer(id, freelancerDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFreelancer(@PathVariable Long id) {
        freelancerService.markFreelancerDeleted(id);      
        return ResponseEntity.ok("Freelancer with ID " + id + " has been marked for deletion.");
    }

    @GetMapping("/newFreelancers")
    public List<Freelancer> getNewFreelancers() {
        return freelancerService.getNewFreelancers();
    }
  
    @PutMapping("/{id}/approve")
    public Freelancer approveFreelancer(@PathVariable Long id) {
        return freelancerService.approveFreelancer(id);
    }
   
    @GetMapping("/deleted")
    public List<Freelancer> getDeletedFreelancersInLast7Days() {
        return freelancerService.getDeletedFreelancersInLast7Days();
    }
}
