package com.freelancers.freelancers_app.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.freelancers.freelancers_app.event.FreelancerEvent;
import com.freelancers.freelancers_app.service.NotificationService;

@Component
public class FreelancerEventListener {

    @Autowired
    private NotificationService notificationService;

    @EventListener
    public void handleFreelancerEvent(FreelancerEvent event) {        
        notificationService.saveNotification(event.getEventType(), event.getFreelancerId());
    }
}
