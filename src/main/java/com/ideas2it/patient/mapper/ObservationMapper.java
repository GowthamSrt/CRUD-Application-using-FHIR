package com.ideas2it.patient.mapper;

import com.ideas2it.patient.dto.ObservationDto;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Reference;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Component
public class ObservationMapper {

    public Observation toEntity(ObservationDto observationDto) {
        Observation observation = new Observation();
        observation.addIdentifier()
                .setSystem("http://acme.org/mrns")
                .setValue(UUID.randomUUID().toString());
        Optional.ofNullable(observationDto.getStatus())
                .ifPresent(status -> observation.setStatus(Observation.ObservationStatus.fromCode(status)));
        Optional.ofNullable(observationDto.getCode())
                .ifPresent(code -> observation.getCode()
                        .addCoding()
                        .setSystem("http://locin.org")
                        .setCode(code));
        Optional.ofNullable(observationDto.getValue())
                .ifPresent(value -> observation.setValue(new Quantity()
                        .setValue(new BigDecimal(value))
                        .setUnit(observationDto.getUnit())));
        if (observationDto.getPatientId() != null) {
            observation.setSubject(new Reference("Patient/" + observationDto.getPatientId()));
        }
        return observation;
    }

    public ObservationDto toDto(Observation observation) {
        ObservationDto observationDto = new ObservationDto();
        observationDto.setId(observation.getIdElement().getIdPart());
        observationDto.setStatus(observation.getStatus().toCode());
        if (observation.hasCode() && !observation.getCode().getCoding().isEmpty()) {
            observationDto.setCode(observation.getCode().getCodingFirstRep().getCode());
        }
        if (observation.hasValueQuantity()) {
            observationDto.setValue(observation.getValueQuantity().getValue().toString());
            observationDto.setUnit(observation.getValueQuantity().getUnit());
        }
        if (observation.hasSubject()) {
            observationDto.setPatientId(observation.getSubject().getReference());
        }
        return observationDto;
    }
}