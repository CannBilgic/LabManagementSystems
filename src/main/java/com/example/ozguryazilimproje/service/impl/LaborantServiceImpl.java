package com.example.ozguryazilimproje.service.impl;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.exceptions.LaborantNotCreatedException;
import com.example.ozguryazilimproje.exception.exceptions.LaborantNotFoundException;
import com.example.ozguryazilimproje.exception.exceptions.PatientNotFoundException;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import com.example.ozguryazilimproje.repository.entity.Laborant;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.repository.repository.LaborantRepository;
import com.example.ozguryazilimproje.request.LaborantCreateRequest;
import com.example.ozguryazilimproje.request.LaborantUpdateRequest;
import com.example.ozguryazilimproje.response.LaborantResponse;
import com.example.ozguryazilimproje.service.LaborantService;
import com.example.ozguryazilimproje.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class LaborantServiceImpl implements LaborantService {
    private final LaborantRepository laborantRepository;
    private  final RoleService roleService;
    @Override
    public Laborant createLaborant(Language language, LaborantCreateRequest laborantCreateRequest) {
        log.debug("[{}][CreateLaborant] -> request{}",this.getClass().getSimpleName(),laborantCreateRequest);
        Role role=roleService.getRole(language,laborantCreateRequest.getRoleId().getRoleId());
        try {
            Laborant laborant = Laborant.builder()
                    .laborantName(laborantCreateRequest.getLaborantName())
                    .laborantSurName(laborantCreateRequest.getLaborantSurName())
                    .password(laborantCreateRequest.getPassword())
                    .hospitalIdNo(laborantCreateRequest.getHospitalIdNo())
                    .userName(laborantCreateRequest.getUserName())
                    .role(role).build();
            Laborant laborantResponse = laborantRepository.save(laborant);
            log.debug("[{}][CreateLaborant] -> request{}",this.getClass().getSimpleName(),laborantResponse);
            return laborantResponse;

        }catch (Exception exception){
            throw  new LaborantNotCreatedException(language, FriendlyMessageCodes.LABORANT_NOT_CREATED_EXCEPTION,"Laborant request:"+laborantCreateRequest.toString());
        }
    }

    @Override
    public List<Laborant> getAllLaborants(Language language) {
        log.debug("[{}][getAllLaborants] -> request{}",this.getClass().getSimpleName(),this.getClass().toString());
        List<Laborant> laborants= laborantRepository.findAll();
        if (laborants.isEmpty()){
            throw  new LaborantNotFoundException(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION,"Laborant Not Found");
        }
        log.debug("[{}][getAllLaborants] -> request{}",this.getClass().getSimpleName(),laborants);
        return laborants;
    }

    @Override
    public Boolean deleteLaborant(Language language, Long laborantId) {
        log.debug("[{}][DeleteLaborant] -> request{}",this.getClass().getSimpleName(),laborantId);
        if (laborantRepository.findById(laborantId).isEmpty()){
            throw  new LaborantNotFoundException(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION,"Laborant not found");
        }else {
            laborantRepository.deleteById(laborantId);
            return true;
        }


    }

    @Override
    public Laborant updateLaborant(Language language, Long laborantId, LaborantUpdateRequest laborantUpdateRequest) {
        log.debug("[{}][UpdateLaborant] -> request{}",this.getClass().getSimpleName(),laborantUpdateRequest);
        Laborant laborant = laborantRepository.findById(laborantId).get();
        Role role=roleService.getRole(language,laborantUpdateRequest.getRoleId().getRoleId());
        laborant.setLaborantName(laborantUpdateRequest.getLaborantName());
        laborant.setLaborantSurName(laborantUpdateRequest.getLaborantSurName());
        laborant.setPassword(laborantUpdateRequest.getPassword());
        laborant.setHospitalIdNo(laborantUpdateRequest.getHospitalIdNo());
        laborant.setUserName(laborantUpdateRequest.getUserName());
        laborant.setRole(role);
        Laborant laborantResponse = laborantRepository.save(laborant);
        log.debug("[{}][UpdateLaborant] -> request{}",this.getClass().getSimpleName(),laborantResponse);


        return laborantResponse;
    }

    @Override
    public List<Laborant> getByNameAndSurname(Language language,String laborantName, String laborantSurName) {
        log.debug("[{}][getByNameAndSurname] -> request{}",this.getClass().getSimpleName(),laborantName,laborantSurName);
        try {
            List<Laborant> laborant = laborantRepository.getByNameAndSurname(laborantName,laborantSurName);
            return laborant;
        }catch (Exception exception){
            throw  new LaborantNotFoundException(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION,"Laborant not found");

        }

    }

    @Override
    public Laborant getLaborant(Language language,Long laborantId) {
        try {
            Laborant laborant =laborantRepository.findById(laborantId).get();
            return laborant;
        }catch (Exception exception){
            throw new LaborantNotFoundException(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION,"Laborant not found");
        }


    }

    @Override
    public Laborant Login(Language language, String userName, String password) {
        log.debug("[{}][Login] -> request{}",this.getClass().getSimpleName(),userName,password);
        try {
            Laborant laborant =laborantRepository.Login(userName,password);
            return laborant;
        }catch (Exception exception){
            throw  new LaborantNotFoundException(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION,"Laborant Not Found Exception");
        }
    }

}
