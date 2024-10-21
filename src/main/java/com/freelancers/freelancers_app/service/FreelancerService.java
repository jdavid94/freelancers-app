package com.freelancers.freelancers_app.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.freelancers.freelancers_app.dto.FreelancerDTO;
import com.freelancers.freelancers_app.entity.Freelancer;
import com.freelancers.freelancers_app.event.FreelancerEvent;
import com.freelancers.freelancers_app.exception.ResourceNotFoundException;
import com.freelancers.freelancers_app.repository.FreelancerRepository;

@Service
public class FreelancerService {

    @Autowired
    private FreelancerRepository freelancerRepository; 

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final String FREELANCER_NOT_FOUND = "Freelancer not found";

    private static final int LAST_DAYS = 7;

    @Async
    public Freelancer registerFreelancer(FreelancerDTO freelancerDTO) {
        Freelancer freelancer = new Freelancer();
        freelancer.setFirstName(freelancerDTO.getFirstName());
        freelancer.setLastName(freelancerDTO.getLastName());
        freelancer.setDateOfBirth(freelancerDTO.getDateOfBirth());
        freelancer.setGender(freelancerDTO.getGender());
        freelancer.setStatus(Freelancer.Status.NEW_FREELANCER); 
        Freelancer savedFreelancer = freelancerRepository.save(freelancer);
      
        eventPublisher.publishEvent(new FreelancerEvent(this, "CREATED", 
            freelancer.getId().toString()));

        return savedFreelancer;
    }

    public List<Freelancer> getAllFreelancers() {
        return freelancerRepository.findAll();
    }

    @Async
    public Freelancer updateFreelancer(Long id, FreelancerDTO freelancerDetails) {
        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FREELANCER_NOT_FOUND));

        freelancer.setFirstName(freelancerDetails.getFirstName());
        freelancer.setLastName(freelancerDetails.getLastName());
        freelancer.setDateOfBirth(freelancerDetails.getDateOfBirth());
        freelancer.setGender(freelancerDetails.getGender());
       
        eventPublisher.publishEvent(new FreelancerEvent(this, "UPDATED",
                freelancer.getId().toString()));

        return freelancerRepository.save(freelancer);
    }

    @Async
    public void markFreelancerDeleted(Long id) {
        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FREELANCER_NOT_FOUND));

        freelancer.setStatus(Freelancer.Status.DELETED);
        freelancer.setDeletedDate(LocalDate.now());  // To save delete date
      
        eventPublisher.publishEvent(new FreelancerEvent(this, "DELETED",
                freelancer.getId().toString()));

        freelancerRepository.save(freelancer);
    }

    public List<Freelancer> getNewFreelancers() {
        return freelancerRepository.findByStatus(Freelancer.Status.NEW_FREELANCER);
    }

    public Freelancer approveFreelancer(Long id) {
        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FREELANCER_NOT_FOUND));

        freelancer.setStatus(Freelancer.Status.VERIFIED);
        return freelancerRepository.save(freelancer);
    }

    public List<Freelancer> getDeletedFreelancersInLast7Days() {
        return freelancerRepository.findByStatusAndDeletedDateAfter(
                Freelancer.Status.DELETED, LocalDate.now().minusDays(LAST_DAYS));
    }

}
