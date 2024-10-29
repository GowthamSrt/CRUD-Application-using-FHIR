package com.ideas2it.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String id;
    private String name;
    private String gender;
    private LocalDate dob;
    private String phoneNumber;
    private String email;
    private String address;
}
