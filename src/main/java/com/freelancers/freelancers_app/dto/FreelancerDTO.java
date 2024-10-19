package com.freelancers.freelancers_app.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FreelancerDTO {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String gender;
}
