package com.freelancers.freelancers_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import java.time.LocalDate;

import com.freelancers.freelancers_app.dto.FreelancerDTO;
import com.freelancers.freelancers_app.entity.Freelancer;
import com.freelancers.freelancers_app.event.FreelancerEvent;
import com.freelancers.freelancers_app.repository.FreelancerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FreelancerServiceTest {

    @InjectMocks
    private FreelancerService freelancerService;

    @Mock
    private FreelancerRepository freelancerRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;    


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    void registerFreelancerTest() {      
      
        FreelancerDTO freelancerDTO = new FreelancerDTO();
        freelancerDTO.setFirstName("David");
        freelancerDTO.setLastName("Doe");
        freelancerDTO.setDateOfBirth(LocalDate.parse("1990-01-01"));
        freelancerDTO.setGender("M");

        Freelancer freelancer = new Freelancer();
        freelancer.setId(1L);
        freelancer.setFirstName("David");
        freelancer.setLastName("Doe");
        freelancer.setStatus(Freelancer.Status.NEW_FREELANCER);

        when(freelancerRepository.save(any(Freelancer.class))).thenAnswer(i -> {
            Freelancer saved = i.getArgument(0);
            saved.setId(1L); 
            return saved;
        });
        
        Freelancer result = freelancerService.registerFreelancer(freelancerDTO);
     
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("David", result.getFirstName());
        assertEquals(Freelancer.Status.NEW_FREELANCER, result.getStatus());
        verify(freelancerRepository).save(any(Freelancer.class));
        verify(eventPublisher).publishEvent(any(FreelancerEvent.class));
    }


    @Test
    public void testGetAllFreelancers() {
        Freelancer freelancer = new Freelancer();
        freelancer.setFirstName("Jhoa");
        freelancer.setLastName("Smith");
        freelancer.setDateOfBirth(LocalDate.parse("1990-01-01"));
        freelancer.setGender("Female");

        when(freelancerRepository.findAll()).thenReturn(Arrays.asList(freelancer));

        List<Freelancer> result = freelancerService.getAllFreelancers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jhoa", result.get(0).getFirstName());
        verify(freelancerRepository, times(1)).findAll();
    }

    @Test
    void updateFreelancerTest() {
       
        FreelancerDTO freelancerDTO = new FreelancerDTO();
        freelancerDTO.setFirstName("Jhoa");
        freelancerDTO.setLastName("Doe");
        freelancerDTO.setDateOfBirth(LocalDate.parse("1985-01-01"));
        freelancerDTO.setGender("F");

        Freelancer freelancer = new Freelancer();
        freelancer.setId(1L);
        freelancer.setFirstName("David");

        when(freelancerRepository.findById(anyLong())).thenReturn(Optional.of(freelancer));
        when(freelancerRepository.save(any(Freelancer.class))).thenReturn(freelancer);

        Freelancer result = freelancerService.updateFreelancer(1L, freelancerDTO);

        assertNotNull(result);
        assertEquals("Jhoa", result.getFirstName());
        verify(freelancerRepository).findById(1L);
        verify(freelancerRepository).save(any(Freelancer.class));
        verify(eventPublisher).publishEvent(any(FreelancerEvent.class));
    }

    @Test
    void markFreelancerDeletedTest() {
       
        Freelancer freelancer = new Freelancer();
        freelancer.setId(1L);
        freelancer.setFirstName("David");
        freelancer.setStatus(Freelancer.Status.NEW_FREELANCER);

        when(freelancerRepository.findById(anyLong())).thenReturn(Optional.of(freelancer));

        freelancerService.markFreelancerDeleted(1L);

        assertEquals(Freelancer.Status.DELETED, freelancer.getStatus());
        assertEquals(LocalDate.now(), freelancer.getDeletedDate());
        verify(freelancerRepository).save(freelancer);
        verify(eventPublisher).publishEvent(any(FreelancerEvent.class));
    }

    @Test
    void getNewFreelancersTest() {
     
        Freelancer freelancer1 = new Freelancer();
        freelancer1.setFirstName("David");
        freelancer1.setStatus(Freelancer.Status.NEW_FREELANCER);

        Freelancer freelancer2 = new Freelancer();
        freelancer2.setFirstName("Jhoa");
        freelancer2.setStatus(Freelancer.Status.NEW_FREELANCER);

        when(freelancerRepository.findByStatus(Freelancer.Status.NEW_FREELANCER))
                .thenReturn(Arrays.asList(freelancer1, freelancer2));

        List<Freelancer> freelancers = freelancerService.getNewFreelancers();

        assertEquals(2, freelancers.size());
        verify(freelancerRepository).findByStatus(Freelancer.Status.NEW_FREELANCER);
    }

    @Test
    void approveFreelancerTest() {

        Freelancer freelancer = new Freelancer();
        freelancer.setId(1L);
        freelancer.setFirstName("David");
        freelancer.setStatus(Freelancer.Status.NEW_FREELANCER);

        when(freelancerRepository.findById(anyLong())).thenReturn(Optional.of(freelancer));
        when(freelancerRepository.save(any(Freelancer.class))).thenReturn(freelancer);

        Freelancer result = freelancerService.approveFreelancer(1L);

        assertEquals(Freelancer.Status.VERIFIED, result.getStatus());
        verify(freelancerRepository).findById(1L);
        verify(freelancerRepository).save(freelancer);
    }

    @Test
    void getDeletedFreelancersInLast7DaysTest() {
        // Datos de prueba
        Freelancer freelancer1 = new Freelancer();
        freelancer1.setFirstName("David");
        freelancer1.setStatus(Freelancer.Status.DELETED);
        freelancer1.setDeletedDate(LocalDate.now().minusDays(5));

        Freelancer freelancer2 = new Freelancer();
        freelancer2.setFirstName("Jhoa");
        freelancer2.setStatus(Freelancer.Status.DELETED);
        freelancer2.setDeletedDate(LocalDate.now().minusDays(6));

        when(freelancerRepository.findByStatusAndDeletedDateAfter(
                eq(Freelancer.Status.DELETED), any(LocalDate.class)))
                .thenReturn(Arrays.asList(freelancer1, freelancer2));

        List<Freelancer> deletedFreelancers = freelancerService.getDeletedFreelancersInLast7Days();

        assertEquals(2, deletedFreelancers.size());
        verify(freelancerRepository).findByStatusAndDeletedDateAfter(
                eq(Freelancer.Status.DELETED), any(LocalDate.class));
    }
}
