package com.example.ozguryazilimproje.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReportResponse {

    private Long reportId;
    private Long fileNo;

    private PatientReportResponse patient;

    private LaborantReportResponse laborant;

    private  ImageResponse image;

    private  String diagnosis;

    private String diagnosisDetail;

    private Long  CreatedDate ;
}
