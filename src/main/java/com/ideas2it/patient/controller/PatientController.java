package com.ideas2it.patient.controller;

import com.ideas2it.patient.dto.PatientDto;
import com.ideas2it.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PatientController is a REST controller that handles HTTP requests
 * related to patient resources. It provides endpoints for creating,
 * retrieving, updating, and deleting patient information.
 *
 * @author Gowthamraj
 * @version 1.0
 * @since 05-11-2024
 */
@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    /**
     * Creates a new patient resource.
     *
     * @param patientDto The data transfer object containing patient details.
     * @return ResponseEntity containing the created patient resource and HTTP status.
     */
    @PostMapping
    public PatientDto addPatient(@Valid @RequestBody PatientDto patientDto) {
        return patientService.addPatient(patientDto);
    }

    /**
     * Retrieves a patient resource by its ID.
     *
     * @param id The ID of the patient to retrieve.
     * @return ResponseEntity containing the patient resource and HTTP status.
     */
    @GetMapping("/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    /**
     * Updates an existing patient resource.
     *
     * @param id The ID of the patient to update.
     * @param patientDto The data transfer object containing updated patient details.
     * @return ResponseEntity containing the updated patient resource and HTTP status.
     */
    @PutMapping("/{id}")
    public PatientDto updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDto) {
        return patientService.updatePatient(id, patientDto);
    }

    /**
     * Deletes a patient resource by its ID.
     *
     * @param id The ID of the patient to delete.
     * @return ResponseEntity with HTTP status indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public String removePatient(@PathVariable Long id) {
        patientService.removePatient(id);
        return "Patient deleted successfully!";
    }
}
