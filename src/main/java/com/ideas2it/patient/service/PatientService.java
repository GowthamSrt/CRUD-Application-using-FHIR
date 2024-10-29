package com.ideas2it.patient.service;

import com.ideas2it.patient.dto.PatientDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public interface PatientService {
    PatientDto addPatient(PatientDto patientDto);

    PatientDto getPatientById(Long id);

    PatientDto updatePatient(Long id, PatientDto patientDto);

    String removePatient(Long id);

    List<PatientDto> getAllPatients();
}