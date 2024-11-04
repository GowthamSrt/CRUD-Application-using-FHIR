package com.ideas2it.patient.service.impl;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.ideas2it.patient.dto.ObservationDto;
import com.ideas2it.patient.mapper.ObservationMapper;
import com.ideas2it.patient.service.ObservationService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.utilities.json.JsonException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObservationServiceImpl implements ObservationService {

    private static final String SERVER = "https://hapi.fhir.org/baseR4";
    private final static FhirContext fhirContext;
    private final static IGenericClient client;
    private final ObservationMapper observationMapper;
    private final ObjectMapper objectMapper;

    static {
        fhirContext = FhirContext.forR4();
        client = fhirContext.newRestfulGenericClient(SERVER);
    }

    @Override
    public ObservationDto addObservation(ObservationDto observationDto) {
        val observation = observationMapper.toEntity(observationDto);
        if (observationDto.getPatientId() != null) {
            observation.setSubject(new Reference(observationDto.getPatientId()));
        }
        val createdObservation = (Observation) client.create()
                .resource(observation)
                .execute()
                .getResource();
        return observationMapper.toDto(createdObservation);
    }

    @Override
    public ObservationDto getObservationById(Long id) {
        val observation = (Observation) client.read()
                .resource(Observation.class)
                .withId(id)
                .execute();
        return observationMapper.toDto(observation);
    }

//    @Override
//    public ObservationDto updateObservation(Long id, ObservationDto observationDto) {
//        val observation = observationMapper.toEntity(observationDto);
//        observation.setId(id.toString());
//        val updatedObservation = (Observation) client.update()
//                .resource(observation)
//                .execute()
//                .getResource();
//        return observationMapper.toDto(updatedObservation);
//    }

//    @Override
//    public ObservationDto updateObservation(Long id, JsonPatch patch) {
//        Observation existingObservation = client.read()
//                .resource(Observation.class)
//                .withId(id.toString())
//                .execute();
//        Observation patchedObservation = applyPatchToObservation(patch, existingObservation);
//        Observation updatedObservation = (Observation) client.update()
//                .resource(patchedObservation)
//                .execute()
//                .getResource();
//        return observationMapper.toDto(updatedObservation);
//    }
//
//    private Observation applyPatchToObservation(JsonPatch patch, Observation observation) {
//        try {
//            JsonNode observationNode = objectMapper.convertValue(observation, JsonNode.class);
//            JsonNode patchedNode = patch.apply(observationNode);
//            return objectMapper.treeToValue(patchedNode, Observation.class);
//        } catch (JsonProcessingException | JsonPatchException e) {
//            throw new RuntimeException("Failed to apply Json Patch", e);
//        }
//    }

    @Override
    public String deleteObservation(Long id) {
        val observation = (Observation) client.read()
                .resource(Observation.class)
                .withId(id)
                .execute();
        client.delete()
                .resource(observation)
                .execute();
        return "Observation Deleted Successfully!";
    }

    @Override
    public List<ObservationDto> getAllObservations() {
        Bundle bundle = client.search()
                .forResource(Observation.class)
                .returnBundle(Bundle.class)
                .execute();
        List<ObservationDto> observations = bundle.getEntry()
                .stream()
                .map(observation -> (Observation) observation.getResource())
                .map(observationMapper :: toDto)
                .toList();
        return observations;
    }

    @Override
    public ObservationDto updateObservation(Long id, JsonPatch patch) {
        Observation existingObservation = client.read()
                .resource(Observation.class)
                .withId(id.toString())
                .execute();
        ObservationDto observationDto = observationMapper.toDto(existingObservation);
        ObservationDto patchedDto = applyPatchToObservationDto(patch, observationDto);
        Observation patchedObservation = observationMapper.toEntity(patchedDto);
        patchedObservation.setId(id.toString());
        Observation updatedObservation = (Observation) client.update()
                .resource(patchedObservation)
                .execute()
                .getResource();
        return observationMapper.toDto(updatedObservation);
    }

    private ObservationDto applyPatchToObservationDto(JsonPatch patch, ObservationDto observationDto) {
        try {
            JsonNode observationNode = objectMapper.convertValue(observationDto, JsonNode.class);
            JsonNode patchedNode = patch.apply(observationNode);
            return objectMapper.treeToValue(patchedNode, ObservationDto.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException("Failed to apply JSON patch to Observation DTO", e);
        }
    }
}
