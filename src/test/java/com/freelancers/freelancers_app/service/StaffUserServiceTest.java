package com.freelancers.freelancers_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.freelancers.freelancers_app.dto.StaffUserDTO;
import com.freelancers.freelancers_app.entity.StaffUser;
import com.freelancers.freelancers_app.repository.StaffUserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StaffUserServiceTest {

    @InjectMocks
    private StaffUserService staffUserService;

    @Mock
    private StaffUserRepository staffUserRepository;

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
    public void testCreateStaffUser() {
        when(staffUserRepository.save(any(StaffUser.class))).thenReturn(staffUser);

        StaffUser result = staffUserService.createStaffUser(staffUserDTO);

        assertNotNull(result);
        assertEquals("David", result.getFirstName());
        verify(staffUserRepository, times(1)).save(any(StaffUser.class));
    }

    @Test
    public void testGetAllStaffUsers() {
        when(staffUserRepository.findAll()).thenReturn(Arrays.asList(staffUser));

        List<StaffUser> result = staffUserService.getAllStaffUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("David", result.get(0).getFirstName());
        verify(staffUserRepository, times(1)).findAll();
    }

    @Test
    public void testGetStaffUserById() {
        when(staffUserRepository.findById(anyLong())).thenReturn(Optional.of(staffUser));

        StaffUser result = staffUserService.getStaffUserById(1L);

        assertNotNull(result);
        assertEquals("David", result.getFirstName());
        verify(staffUserRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateStaffUser() {
        when(staffUserRepository.findById(anyLong())).thenReturn(Optional.of(staffUser));
        when(staffUserRepository.save(any(StaffUser.class))).thenReturn(staffUser);

        StaffUserDTO updatedDetails = new StaffUserDTO();
        updatedDetails.setFirstName("Jhoa");
        updatedDetails.setLastName("Smith");

        StaffUser result = staffUserService.updateStaffUser(1L, updatedDetails);

        assertNotNull(result);
        assertEquals("Jhoa", result.getFirstName());
        verify(staffUserRepository, times(1)).findById(1L);
        verify(staffUserRepository, times(1)).save(any(StaffUser.class));
    }

    @Test
    public void testDeleteStaffUser() {
        when(staffUserRepository.findById(anyLong())).thenReturn(Optional.of(staffUser));

        staffUserService.deleteStaffUser(1L);

        verify(staffUserRepository, times(1)).findById(1L);
        verify(staffUserRepository, times(1)).delete(staffUser);
    }
}
