package com.ideas2it.patient.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private String id;

    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Please enter the name")
    @Pattern(regexp = "^[a-zA-z]+([\\s][a-zA-Z]+)*$", message = "Name only contains Characters")
    private String name;

    @NotNull(message = "Gender is mandatory")
    @Pattern(regexp = "^male$|^female$")
    private String gender;

    @NotNull(message = "Date birth is required")
    private LocalDate dob;


    @NotNull(message = "Phone number is mandatory")
    @NotBlank(message = "Please enter the phone number")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number is 10 digits")
    private String phoneNumber;

    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Please enter Valid Email")
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String address;
}
