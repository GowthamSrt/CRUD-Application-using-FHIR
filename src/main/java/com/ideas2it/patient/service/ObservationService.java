package com.ideas2it.patient.service;

import com.ideas2it.patient.dto.ObservationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObservationService {
    ObservationDto addObservation(ObservationDto observationDto);

    ObservationDto getObservationById(Long id);

    ObservationDto updateObservation(Long id, ObservationDto observationDto);

    String deleteObservation(Long id);

    List<ObservationDto> getAllObservations();
}
