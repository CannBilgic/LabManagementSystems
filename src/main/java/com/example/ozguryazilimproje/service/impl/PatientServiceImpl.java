package com.example.ozguryazilimproje.service.impl;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.exceptions.PatientNotCreatedException;
import com.example.ozguryazilimproje.exception.exceptions.PatientNotFoundException;
import com.example.ozguryazilimproje.repository.entity.Patient;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.repository.repository.PatientRepository;
import com.example.ozguryazilimproje.request.PatientCreateRequest;
import com.example.ozguryazilimproje.request.PatientUpdateRequest;
import com.example.ozguryazilimproje.service.PatientService;
import com.example.ozguryazilimproje.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private  final PatientRepository patientRepository;
    private  final RoleService roleService;
    @Override
    public Patient createPatient(Language language, PatientCreateRequest patientCreateRequest) {
        try {
            log.debug("[{}][createPatient] -> request{}",this.getClass().getSimpleName(),patientCreateRequest);
            Role role=roleService.getRole(language,patientCreateRequest.getRoleId().getRoleId());
            Patient patient= Patient.builder()
                    .TC(patientCreateRequest.getTC())
                    .name(patientCreateRequest.getName())
                    .surName(patientCreateRequest.getSurName())
                    .password(patientCreateRequest.getPassword())
                    .userName(patientCreateRequest.getUserName())
                    .role(role)
                    .build();
            Patient patientResponse=patientRepository.save(patient);
            log.debug("[{}][createPatient] -> request{}",this.getClass().getSimpleName(),patientResponse);
            return patientResponse;

        }catch (Exception exception){
            throw  new PatientNotCreatedException(language, FriendlyMessageCodes.PATIENT_NOT_CREATED_EXCEPTION,"Patient not created");
        }
    }

    @Override
    public Patient updatePatient(Language language, Long patientId, PatientUpdateRequest patientUpdateRequest) {
        log.debug("[{}][updatePatient] -> request{}",this.getClass().getSimpleName(),patientId);
        Patient patient = patientRepository.findById(patientId).get();
        Role role=roleService.getRole(language,patientUpdateRequest.getRole().getRoleId());
        patient.setTC(patientUpdateRequest.getTC());
        patient.setName(patientUpdateRequest.getName());
        patient.setSurName(patientUpdateRequest.getSurName());
        patient.setPassword(patientUpdateRequest.getPassword());
        patient.setUserName(patientUpdateRequest.getUserName());
        patient.setRole(role);
        Patient patientResponse=patientRepository.save(patient);
        log.debug("[{}][updatePatient] -> request{}",this.getClass().getSimpleName(),patientResponse);
        return patientResponse;
    }

    @Override
    public Boolean deletePatient(Language language, Long patientId) {
        log.debug("[{}][deletePatient] -> request{}",this.getClass().getSimpleName(),patientId);
        if (patientRepository.findById(patientId).isEmpty()){
            throw new PatientNotFoundException(language,FriendlyMessageCodes.PATIENT_NOT_FOUND_EXCEPTION,"Patient Not Found");
        }else {
            patientRepository.deleteById(patientId);
            return true;
        }
    }

    @Override
    public List<Patient> getAllPatient(Language language) {
        log.debug("[{}][getAllPatient] -> request{}",this.getClass().getSimpleName(),this.getClass().getSimpleName());
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty()){
            throw new PatientNotFoundException(language,FriendlyMessageCodes.PATIENT_NOT_FOUND_EXCEPTION,"Patient Not Found");
        }else {
            log.debug("[{}][getAllPatient] -> request{}",this.getClass().getSimpleName(),patients);
            return patients;
        }

    }

    @Override
    public Patient getOnePatient(Language language, Long patientId) {
        log.debug("[{}][getOnePatient] -> request{}",this.getClass().getSimpleName(),patientId);
        try {
            Patient patient = patientRepository.findById(patientId).get();
            return patient;
        }catch (Exception exception){
            throw  new PatientNotFoundException(language,FriendlyMessageCodes.PATIENT_NOT_FOUND_EXCEPTION,"Patient Not Found Exception");
        }
    }



    @Override
    public List<Patient>  getByNameAndSurname(Language language,String name, String surName,Long TC) {
        log.debug("[{}][getByNameAndSurname] -> request{}",this.getClass().getSimpleName(),name,surName);
        try {
            List<Patient>  patient = patientRepository.getByNameAndSurname(name,surName,TC);
            return patient;
        }catch (Exception exception){
            throw  new PatientNotFoundException(language,FriendlyMessageCodes.PATIENT_NOT_FOUND_EXCEPTION,"Patient Not Found Exception");
        }
    }

    @Override
    public Patient Login(Language language,String userName, String password) {
        log.debug("[{}][Login] -> request{}",this.getClass().getSimpleName(),userName,password);
        try {
            Patient patient = patientRepository.Login(userName,password);
            return patient;
        }catch (Exception exception){
            throw  new PatientNotFoundException(language,FriendlyMessageCodes.PATIENT_NOT_FOUND_EXCEPTION,"Patient Not Found Exception");
        }
    }
}
