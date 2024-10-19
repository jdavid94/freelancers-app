package com.freelancers.freelancers_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelancers.freelancers_app.entity.StaffUser;

public interface StaffUserRepository extends JpaRepository<StaffUser, Long> {

}
