package com.freelancers.freelancers_app.event;

import org.springframework.context.ApplicationEvent;

public class FreelancerEvent extends ApplicationEvent {

    private final String eventType;
    private final String freelancerId;

    public FreelancerEvent(Object source, String eventType, String freelancerId) {
        super(source);
        this.eventType = eventType;
        this.freelancerId = freelancerId;    
    }

    public String getEventType() {
        return eventType;
    }

    public String getFreelancerId() {
        return freelancerId;
    }
}
