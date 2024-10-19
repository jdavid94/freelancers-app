package com.freelancers.freelancers_app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancers.freelancers_app.entity.Freelancer;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {

    List<Freelancer> findByStatus(Freelancer.Status status);

    List<Freelancer> findByStatusAndDeletedDateAfter(Freelancer.Status status, LocalDate date);
    
}
