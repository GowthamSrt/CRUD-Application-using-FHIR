package com.ideas2it.patient.service;

import com.github.fge.jsonpatch.JsonPatch;
import com.ideas2it.patient.dto.ObservationDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObservationService {
    ObservationDto addObservation(ObservationDto observationDto);

    ObservationDto getObservationById(Long id);

    ObservationDto updateObservation(Long id, JsonPatch patch);

    String deleteObservation(Long id);

    List<ObservationDto> getAllObservations();
}
