package com.example.ozguryazilimproje.request;


import com.example.ozguryazilimproje.repository.entity.Image;
import lombok.Data;

import java.util.Optional;

@Data
public class ReportCreateRequest {

    private Long fileNo;

    private PatientGetRequest patientId;

    private LaborantGetRequest laborantId;

    private ImageGetRequest image;

    private  String diagnosis;

    private String diagnosisDetail;

}
