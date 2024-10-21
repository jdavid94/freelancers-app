package com.freelancers.freelancers_app.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.freelancers.freelancers_app.entity.Notification;
import com.freelancers.freelancers_app.repository.NotificationRepository;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationRepository notificationRepository;

    private Notification notification;

    @BeforeEach
    public void setUp() {
        notification = new Notification();
        notification.setEventType("CREATED");
        notification.setFreelancerId("1");
        notification.setTimestamp(LocalDateTime.now());
    }

    @Test
    public void testSaveNotification() {
        notificationService.saveNotification("CREATED", "1");

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
}
