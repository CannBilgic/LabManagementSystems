package com.example.ozguryazilimproje.response;

import com.example.ozguryazilimproje.repository.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@AllArgsConstructor
public class PatientResponse  {
    private  Long patientId;
    private Long TC;

    private String name;

    private String surName;
    private String userName;
    private Role role;
}
