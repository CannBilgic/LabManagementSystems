package com.example.ozguryazilimproje.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PatientReportResponse {
    private  Long patientId;
    private Long TC;

    private String name;

    private String surName;
}
