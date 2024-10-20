package com.freelancers.freelancers_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelancers.freelancers_app.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
