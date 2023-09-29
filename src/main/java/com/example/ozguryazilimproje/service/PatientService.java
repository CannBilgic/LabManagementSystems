package com.example.ozguryazilimproje.service;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.repository.entity.Patient;
import com.example.ozguryazilimproje.request.PatientCreateRequest;
import com.example.ozguryazilimproje.request.PatientUpdateRequest;

import java.util.List;

public interface PatientService {
    Patient createPatient(Language language, PatientCreateRequest patientCreateRequest);

    Patient updatePatient(Language language, Long patientId, PatientUpdateRequest patientUpdateRequest);

    Boolean deletePatient(Language language,Long patientId);

    List<Patient> getAllPatient(Language language);

    Patient getOnePatient(Language language,Long patientId);

    List<Patient>  getByNameAndSurname(Language language,String name, String surName,Long TC);

    Patient Login(Language language,String userName, String password);

}
