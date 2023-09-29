package com.example.ozguryazilimproje.controller;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import com.example.ozguryazilimproje.repository.entity.Patient;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.request.PatientCreateRequest;
import com.example.ozguryazilimproje.request.PatientUpdateRequest;
import com.example.ozguryazilimproje.response.FriendlyMessage;
import com.example.ozguryazilimproje.response.InternalApiResponse;
import com.example.ozguryazilimproje.response.PatientResponse;
import com.example.ozguryazilimproje.service.PatientService;
import com.example.ozguryazilimproje.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/patient")
@RequiredArgsConstructor
@CrossOrigin
public class PatientController {
    private  final PatientService patientService;
    private final RoleService roleService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{language}/create")
    @CrossOrigin
    public InternalApiResponse<PatientResponse> createPatient (@PathVariable(name = "language")Language language,
                                                               @RequestBody PatientCreateRequest patientCreateRequest){

        log.debug("[{}][createPatient] -> request{}",this.getClass().getSimpleName(),patientCreateRequest);
        Patient patient = patientService.createPatient(language,patientCreateRequest);
        PatientResponse patientResponse= PatientResponse.builder()
                .patientId(patient.getPatientId())
                .TC(patient.getTC())
                .name(patient.getName())
                .surName(patient.getSurName())
                .role(roleService.getRole(language,patient.getRole().getRoleId()))
                .build();
        log.debug("[{}][createPatient] -> request{}",this.getClass().getSimpleName(),patientResponse);
        return  InternalApiResponse.<PatientResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PATIENT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(patientResponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{patientId}")
    public  InternalApiResponse<PatientResponse> updatePatient(@PathVariable(name ="language")Language language,
                                                               @PathVariable Long patientId,
                                                               @RequestBody PatientUpdateRequest patientUpdateRequest){

        log.debug("[{}][updatePatient] -> request{}",this.getClass().getSimpleName(),patientId);
        Patient patient = patientService.updatePatient(language,patientId,patientUpdateRequest);
        PatientResponse patientResponse = PatientResponse.builder()
                .patientId(patient.getPatientId())
                .TC(patient.getTC())
                .name(patient.getName())
                .surName(patient.getSurName())
                .role(roleService.getRole(language,patient.getRole().getRoleId()))
                .build();
        log.debug("[{}][updatePatient] -> request{}",this.getClass().getSimpleName(),patientResponse);
        return InternalApiResponse.<PatientResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PATIENT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(patientResponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{patientId}")
    public  InternalApiResponse<PatientResponse> deletePatient(@PathVariable(name = "language")Language language,
                                                               @PathVariable Long patientId){
        log.debug("[{}][deletePatient] -> request{}",this.getClass().getSimpleName(),patientId);
        if (patientService.deletePatient(language,patientId)){
            return InternalApiResponse.<PatientResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PATIENT_SUCCESSFULLY_DELETED))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();
        }else{
            return InternalApiResponse.<PatientResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ERROR))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PATIENT_NOT_FOUND_EXCEPTION))
                            .build())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .hasError(true)
                    .build();
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getAll")
    public  InternalApiResponse<List<PatientResponse>>getAll(@PathVariable(name = "language")Language language){
        log.debug("[{}][getAllPatient] -> request{}",this.getClass().getSimpleName());
        List<Patient> patients = patientService.getAllPatient(language);
        List<PatientResponse> patientResponses= patients.stream().map(args ->PatientResponse.builder()
                        .patientId(args.getPatientId())
                .TC(args.getTC())
                .name(args.getName())
                .surName(args.getSurName())
                        .userName(args.getUserName())
                .role(Role.builder().roleId(args.getRole().getRoleId()).userRole(args.getRole().getUserRole()).build())

                .build())
                .collect(Collectors.toList());
        log.debug("[{}][getAllPatient] -> request{}",this.getClass().getSimpleName(),patientResponses);

        return InternalApiResponse.<List<PatientResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(patientResponses)
                .build();

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getOne/{patientId}")
    public  InternalApiResponse<PatientResponse> getOne(@PathVariable(name = "language")Language language,
                                                        @PathVariable Long patientId){

        log.debug("[{}][getOnePatient] -> request{}",this.getClass().getSimpleName(),patientId);
        Patient patient = patientService.getOnePatient(language,patientId);
        PatientResponse patientResponse = PatientResponse.builder()
                .patientId(patient.getPatientId())
                .TC(patient.getTC())
                .name(patient.getName())
                .surName(patient.getSurName())
                .userName(patient.getUserName())
                .role(patient.getRole())
                .build();
        log.debug("[{}][getOnePatient] -> request{}",this.getClass().getSimpleName(),patientResponse);
        return InternalApiResponse.<PatientResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(patientResponse)
                .build();

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{language}/getNameAndSurname")
    public InternalApiResponse<List<PatientResponse>>getByNameAndSurname(@PathVariable(name = "language")Language language,
                                                                     String name,
                                                                     String surName,
                                                                     Long TC) {
        log.debug("[{}][GetNameAndSurname] -> request{}", this.getClass().getSimpleName(), name, surName);
        List<Patient>  patient = patientService.getByNameAndSurname(language, name, surName, TC);

        List<PatientResponse> patientResponses= patient.stream().map(args ->PatientResponse.builder()
                        .patientId(args.getPatientId())
                        .TC(args.getTC())
                        .name(args.getName())
                        .surName(args.getSurName())
                        .userName(args.getUserName())
                        .role(Role.builder().roleId(args.getRole().getRoleId()).userRole(args.getRole().getUserRole()).build())

                        .build())
                .collect(Collectors.toList());

        log.debug("[{}][GetNameAndSurname] -> request{}", this.getClass().getSimpleName(), patientResponses);
        return InternalApiResponse.<List<PatientResponse>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(patientResponses)
                .build();

    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{language}/Login")
    public InternalApiResponse<PatientResponse> Login(@PathVariable(name = "language")Language language,
                                                        String userName,
                                                         String password) {
        log.debug("[{}][Login] -> request{}", this.getClass().getSimpleName(), userName, password);
        Patient patient = patientService.Login(language,  userName, password);
        System.out.println(patient);
        PatientResponse patientResponse = PatientResponse.builder()
                .patientId(patient.getPatientId())
                .TC(patient.getTC())
                .name(patient.getName())
                .surName(patient.getSurName())
                .userName(patient.getUserName())
                .role(patient.getRole())
                .build();

        log.debug("[{}][Login] -> request{}", this.getClass().getSimpleName(), patientResponse);
        return InternalApiResponse.<PatientResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(patientResponse)
                .build();

    }



}
