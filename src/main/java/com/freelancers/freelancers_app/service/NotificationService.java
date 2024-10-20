package com.freelancers.freelancers_app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freelancers.freelancers_app.entity.Notification;
import com.freelancers.freelancers_app.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void saveNotification(String eventType, String freelancerId) {
        Notification notification = new Notification();
        notification.setEventType(eventType);
        notification.setFreelancerId(freelancerId);
        notification.setTimestamp(LocalDateTime.now());        
        notificationRepository.save(notification);
    }
}
