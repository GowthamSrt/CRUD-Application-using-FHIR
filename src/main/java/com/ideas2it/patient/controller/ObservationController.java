package com.ideas2it.patient.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.ideas2it.patient.dto.ObservationDto;
import com.ideas2it.patient.service.ObservationService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/observations")
@RequiredArgsConstructor
public class ObservationController {

    private final ObservationService observationService;

    @PostMapping
    public ResponseEntity<ObservationDto> addObservation(@RequestBody ObservationDto observationDto) {
        val createdObservation = observationService.addObservation(observationDto);
        return new ResponseEntity<>(createdObservation, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationDto> getObservationById(@PathVariable Long id) {
        return new ResponseEntity<>(observationService.getObservationById(id), HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ObservationDto> updateObservation(@PathVariable Long id, @RequestBody ObservationDto observationDto) {
//        return new ResponseEntity<>(observationService.updateObservation(id, observationDto), HttpStatus.OK);
//    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<ObservationDto> updateObservation(@PathVariable Long id, @RequestBody JsonPatch patch) {
        ObservationDto updatedObservation = observationService.updateObservation(id, patch);
        return ResponseEntity.ok(updatedObservation);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteObservation(@PathVariable Long id) {
        observationService.deleteObservation(id);
        return new ResponseEntity<>("Observation Deleted Successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<ObservationDto>> listAllObservations() {
        List<ObservationDto> observationDTOs = observationService.getAllObservations();

        if (observationDTOs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(observationDTOs);
    }
}
