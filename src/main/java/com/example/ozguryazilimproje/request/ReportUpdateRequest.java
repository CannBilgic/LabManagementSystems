package com.example.ozguryazilimproje.request;

import lombok.Data;

@Data
public class ReportUpdateRequest {
    private Long fileNo;
    private PatientGetRequest patientId;

    private ImageGetRequest imageId;

    private  String diagnosis;

    private String diagnosisDetail;
}
