package com.example.ozguryazilimproje.service;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.repository.entity.Laborant;
import com.example.ozguryazilimproje.request.LaborantCreateRequest;
import com.example.ozguryazilimproje.request.LaborantUpdateRequest;

import java.util.List;

public interface LaborantService {
    Laborant createLaborant(Language language, LaborantCreateRequest laborantCreateRequest);

    List<Laborant> getAllLaborants(Language language);

    Boolean deleteLaborant(Language language,Long laborantId);
    Laborant updateLaborant(Language language, Long laborantId, LaborantUpdateRequest laborantUpdateRequest);
    List<Laborant> getByNameAndSurname(Language language,String laborantName,String laborantSurName);

    Laborant getLaborant(Language language,Long laborantId);

    Laborant Login(Language language,String userName, String password);

}
