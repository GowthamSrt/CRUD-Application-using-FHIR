package com.ideas2it.patient.mapper;

import com.ideas2it.patient.dto.PatientDto;
import org.hl7.fhir.r4.model.ContactPoint;
import org.hl7.fhir.r4.model.Enumerations;
import org.hl7.fhir.r4.model.Patient;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class PatientMapper {

    public Patient toEntity(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.addIdentifier()
                .setSystem("http://acme.org/mrns")
                .setValue(UUID.randomUUID().toString());
        patient.addName()
                .setText(patientDto.getName());
        patient.addTelecom()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue(patientDto.getPhoneNumber());
        patient.addTelecom()
                .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                .setValue(patientDto.getEmail());
        switch (patientDto.getGender()) {
            case "male" -> patient.setGender(Enumerations.AdministrativeGender.MALE);
            case "female" -> patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        }
        patient.setBirthDate(Date.from(patientDto.getDob().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        patient.addAddress()
                .setState(patientDto.getAddress());
        return patient;
    }

    public PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getIdElement().getIdPart());
        patientDto.setName(patient.getNameFirstRep().getText());
        patientDto.setGender(patient.getGender().toString());
        patientDto.setDob(patient.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        patientDto.setPhoneNumber(patient.getTelecomFirstRep().getValue());
        patientDto.setEmail(patient.getTelecom().get(1).getValue());
        patientDto.setAddress(patient.getAddressFirstRep().getState());
        return patientDto;
    }

    public Patient toEntityWithId(PatientDto patientDto) {
        var patient = new Patient();
        patient.setId(String.valueOf(patient.getId()));
        patient.addIdentifier()
                .setSystem("http://acme.org/mrns")
                .setValue(UUID.randomUUID().toString());
        patient.addName()
                .setText(patientDto.getName());
        patient.addTelecom()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue(patientDto.getPhoneNumber());
        patient.addTelecom()
                .setSystem(ContactPoint.ContactPointSystem.EMAIL)
                .setValue(patientDto.getEmail());
        switch (patientDto.getGender()) {
            case "male" -> patient.setGender(Enumerations.AdministrativeGender.MALE);
            case "female" -> patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        }
        patient.setBirthDate(Date.from(patientDto.getDob().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        patient.addAddress()
                .setState(patientDto.getAddress());
        return patient;
    }

}
