package com.ideas2it.patient.controller;

import com.github.fge.jsonpatch.JsonPatch;
import com.ideas2it.patient.dto.ObservationDto;
import com.ideas2it.patient.service.ObservationService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ObservationController is a REST controller that handles HTTP requests
 * related to observation resources. It provides endpoints for creating,
 * retrieving, updating, and deleting observation information.
 *
 * @author Gowthamraj
 * @version 1.0
 * @since 05-11-2024
 */
@RestController
@RequestMapping("api/v1/observations")
@RequiredArgsConstructor
public class ObservationController {

    private final ObservationService observationService;

    /**
     * Creates a new observation resource.
     *
     * @param observationDto The data transfer object containing observation details.
     * @return ResponseEntity containing the created observation resource and HTTP status.
     */
    @PostMapping
    public ResponseEntity<ObservationDto> addObservation(@RequestBody ObservationDto observationDto) {
        val createdObservation = observationService.addObservation(observationDto);
        return new ResponseEntity<>(createdObservation, HttpStatus.CREATED);
    }

    /**
     * Retrieves an observation resource by its ID.
     *
     * @param id The ID of the observation to retrieve.
     * @return ResponseEntity containing the observation resource and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ObservationDto> getObservationById(@PathVariable Long id) {
        return new ResponseEntity<>(observationService.getObservationById(id), HttpStatus.OK);
    }

    /**
     * Updates an existing observation resource.
     *
     * @param id The ID of the observation to update.
     * @param patch The patched dto to update the required details alone.
     * @return ResponseEntity containing the updated observation resource and HTTP status.
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ObservationDto> updateObservation(@PathVariable Long id, @RequestBody JsonPatch patch) {
        ObservationDto updatedObservation = observationService.updateObservation(id, patch);
        return ResponseEntity.ok(updatedObservation);
    }

    /**
     * Deletes an observation resource by its ID.
     *
     * @param id The ID of the observation to delete.
     * @return ResponseEntity with HTTP status indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteObservation(@PathVariable Long id) {
        observationService.deleteObservation(id);
        return new ResponseEntity<>("Observation Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
