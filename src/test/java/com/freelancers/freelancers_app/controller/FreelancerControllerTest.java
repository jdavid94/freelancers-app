package com.freelancers.freelancers_app.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.freelancers.freelancers_app.dto.FreelancerDTO;
import com.freelancers.freelancers_app.entity.Freelancer;
import com.freelancers.freelancers_app.service.FreelancerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(FreelancerController.class)
public class FreelancerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FreelancerService freelancerService;

    private FreelancerDTO freelancerDTO;
    private Freelancer freelancer;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        freelancerDTO = new FreelancerDTO();
        freelancerDTO.setFirstName("Jhoa");
        freelancerDTO.setLastName("Smith");
        freelancerDTO.setDateOfBirth(LocalDate.parse("1990-01-01"));
        freelancerDTO.setGender("Female");

        freelancer = new Freelancer();
        freelancer.setId(1L);
        freelancer.setFirstName("Jhoa");
        freelancer.setLastName("Smith");
        freelancer.setDateOfBirth(LocalDate.parse("1990-01-01"));
        freelancer.setGender("Female");

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllFreelancers() throws Exception {
        List<Freelancer> freelancers = Arrays.asList(freelancer);
        when(freelancerService.getAllFreelancers()).thenReturn(freelancers);

        mockMvc.perform(get("/api/freelancers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jhoa"));
    }

    @Test
    public void testRegisterFreelancer() throws Exception {
        when(freelancerService.registerFreelancer(any(FreelancerDTO.class))).thenAnswer(i -> {
            Freelancer savedFreelancer = new Freelancer();
            savedFreelancer.setId(1L);  // Ensure the ID is set on save
            savedFreelancer.setFirstName("Jhoa");
            savedFreelancer.setLastName("Smith");
            savedFreelancer.setDateOfBirth(LocalDate.parse("1990-01-01"));
            savedFreelancer.setGender("Female");
            savedFreelancer.setStatus(Freelancer.Status.NEW_FREELANCER);
            return savedFreelancer;
        });

        mockMvc.perform(post("/api/freelancers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(freelancerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jhoa"));
    }

    @Test
    public void testUpdateFreelancer() throws Exception {
        when(freelancerService.updateFreelancer(anyLong(), any(FreelancerDTO.class))).thenReturn(freelancer);

        mockMvc.perform(put("/api/freelancers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(freelancerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jhoa"));
    }

    @Test
    public void testDeleteFreelancer() throws Exception {
        mockMvc.perform(delete("/api/freelancers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Freelancer with ID 1 has been marked for deletion."));
    }

    @Test
    public void testGetNewFreelancers() throws Exception {
        List<Freelancer> freelancers = Arrays.asList(freelancer);
        when(freelancerService.getNewFreelancers()).thenReturn(freelancers);

        mockMvc.perform(get("/api/freelancers/newFreelancers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jhoa"));
    }

    @Test
    public void testApproveFreelancer() throws Exception {
        when(freelancerService.approveFreelancer(anyLong())).thenReturn(freelancer);

        mockMvc.perform(put("/api/freelancers/1/approve")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jhoa"));
    }

    @Test
    public void testGetDeletedFreelancersInLast7Days() throws Exception {
        List<Freelancer> freelancers = Arrays.asList(freelancer);
        when(freelancerService.getDeletedFreelancersInLast7Days()).thenReturn(freelancers);

        mockMvc.perform(get("/api/freelancers/deleted")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jhoa"));
    }
}
