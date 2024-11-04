package com.ideas2it.patient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObservationDto {
    private String id;
    private String status;
    private String code;
    private String value;
    private String unit;
    private String patientId;
}
