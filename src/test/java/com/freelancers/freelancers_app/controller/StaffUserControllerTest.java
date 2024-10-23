package com.freelancers.freelancers_app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freelancers.freelancers_app.dto.StaffUserDTO;
import com.freelancers.freelancers_app.entity.StaffUser;
import com.freelancers.freelancers_app.service.StaffUserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

@WebMvcTest(StaffUserController.class)
public class StaffUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StaffUserService staffUserService;

    private StaffUserDTO staffUserDTO;
    private StaffUser staffUser;

    @BeforeEach
    public void setUp() {
        staffUserDTO = new StaffUserDTO();
        staffUserDTO.setFirstName("David");
        staffUserDTO.setLastName("Doe");

        staffUser = new StaffUser();
        staffUser.setId(1L);
        staffUser.setFirstName("David");
        staffUser.setLastName("Doe");
    }

    @Test
    public void testCreateStaffUser() throws Exception {
        when(staffUserService.createStaffUser(any(StaffUserDTO.class))).thenReturn(staffUser);

        mockMvc.perform(post("/api/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(staffUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("David"));
    }

    @Test
    public void testGetAllStaffUsers() throws Exception {
        when(staffUserService.getAllStaffUsers()).thenReturn(Collections.singletonList(staffUser));

        mockMvc.perform(get("/api/staff")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("David"));
    }

    @Test
    public void testUpdateStaffUser() throws Exception {
        when(staffUserService.updateStaffUser(anyLong(), any(StaffUserDTO.class))).thenReturn(staffUser);

        mockMvc.perform(put("/api/staff/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(staffUserDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("David"));
    }

    @Test
    public void testDeleteStaffUser() throws Exception {
        mockMvc.perform(delete("/api/staff/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
