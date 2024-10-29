package com.ideas2it.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObservationDto {
    private String id;
    private String status;
    private String code;
    private String value;
    private String unit;
    private String patientId;
}
