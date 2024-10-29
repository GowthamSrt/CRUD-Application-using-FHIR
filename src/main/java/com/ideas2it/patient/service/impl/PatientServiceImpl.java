package com.ideas2it.patient.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.ideas2it.patient.dto.PatientDto;
import com.ideas2it.patient.mapper.PatientMapper;
import com.ideas2it.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private static final String SERVER = "https://hapi.fhir.org/baseR4";
    private final static FhirContext fhirContext;
    private final static IGenericClient client;
    private final PatientMapper patientMapper;

    static {
        fhirContext = FhirContext.forR4();
        client = fhirContext.newRestfulGenericClient(SERVER);
    }
    public PatientDto addPatient(PatientDto patientDto) {
        val patient = patientMapper.toEntity(patientDto);
        val addedPatient = (Patient) client.create()
                .resource(patient)
                .execute().getResource();
        return patientMapper.toDto(addedPatient);
    }

    public PatientDto getPatientById(Long id) {
        val patient = (Patient) client.read()
                .resource(Patient.class)
                .withId(id)
                .execute();
        return patientMapper.toDto(patient);
    }

    public PatientDto updatePatient(Long id, PatientDto patientDto) {
        val patient = patientMapper.toEntityWithId(patientDto);
        patient.setId(id.toString());
        val updatedPatient = (Patient) client.update()
                .resource(patient)
                .execute()
                .getResource();
        val updatedPatientDto = patientMapper.toDto(updatedPatient);
        updatedPatientDto.setId(updatedPatient.getIdElement().getIdPart());
        return patientMapper.toDto(updatedPatient);
    }

    public String removePatient(Long id) {
        val patient = (Patient) client.read()
                .resource(Patient.class)
                .withId(id)
                .execute();
        client.delete()
                .resource(patient)
                .execute();
        return "Patient deleted successfully!";
    }

    public List<PatientDto> getAllPatients() {
        val patients = client.search()
                .forResource(Patient.class)
                .returnBundle(Bundle.class)
                .execute();
        return patients.getEntry()
                .stream()
                .map(patient -> (Patient) patient.getResource())
                .map(patientMapper :: toDto)
                .toList();
    }
}
