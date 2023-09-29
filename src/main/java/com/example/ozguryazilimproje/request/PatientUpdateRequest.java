package com.example.ozguryazilimproje.request;

import lombok.Data;

@Data
public class PatientUpdateRequest {
    private Long TC;

    private String name;

    private String surName;

    private String password;
    private  String userName;

    private RoleGetRequest role;
}
