package com.example.ozguryazilimproje.controller;
import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.exceptions.PatientNotFoundException;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import com.example.ozguryazilimproje.repository.entity.Laborant;
import com.example.ozguryazilimproje.repository.entity.Patient;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.request.LaborantCreateRequest;
import com.example.ozguryazilimproje.request.LaborantUpdateRequest;
import com.example.ozguryazilimproje.response.FriendlyMessage;
import com.example.ozguryazilimproje.response.InternalApiResponse;
import com.example.ozguryazilimproje.response.LaborantResponse;
import com.example.ozguryazilimproje.response.PatientResponse;
import com.example.ozguryazilimproje.service.LaborantService;
import com.example.ozguryazilimproje.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/Laborant")
@RequiredArgsConstructor
@CrossOrigin
public class LaborantController {

    private final LaborantService laborantService;
    private  final RoleService roleService;
    @CrossOrigin
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{language}/create")
    public InternalApiResponse<LaborantResponse> createLaborant(@PathVariable(name = "language")Language language,
                                                                @RequestBody LaborantCreateRequest laborantCreateRequest){
        log.debug("[{}][createLaborant] -> request{}",this.getClass().getSimpleName(),laborantCreateRequest);
        Laborant laborant = laborantService.createLaborant(language,laborantCreateRequest);
        LaborantResponse laborantResponse=LaborantResponse.builder().laborantId(laborant.getLaborantId()).laborantName(laborant.getLaborantName())
                        .laborantSurName(laborant.getLaborantSurName()).hospitalIdNo(laborant.getHospitalIdNo())
                        .role(roleService.getRole(language,laborant.getRole().getRoleId())).build();
        log.debug("[{}][createLaborant] -> request{}",this.getClass().getSimpleName(),laborantResponse);

        return InternalApiResponse.<LaborantResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.LABORANT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(laborantResponse)
                .build();
    }
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{language}/getNameAndSurname")
    public InternalApiResponse<List<LaborantResponse>> getByNameAndSurname(@PathVariable(name = "language")Language language,
                                                                      String laborantName,
                                                                      String laborantSurName){
        log.debug("[{}][GetNameAndSurname] -> request{}",this.getClass().getSimpleName(),laborantName,laborantSurName);
        List<Laborant> laborants = laborantService.getByNameAndSurname(language,laborantName,laborantSurName);

        List<LaborantResponse> laborantResponses= laborants.stream().map(arg ->LaborantResponse.builder().laborantId(arg.getLaborantId()).laborantName(arg.getLaborantName())
                .laborantSurName(arg.getLaborantSurName()).hospitalIdNo(arg.getHospitalIdNo())
                .userName(arg.getUserName())
                .role(Role.builder().roleId(arg.getRole().getRoleId()).userRole(arg.getRole().getUserRole()).build()).build()).collect(Collectors.toList());

        log.debug("[{}][GetNameAndSurname] -> request{}",this.getClass().getSimpleName(),laborantResponses);
        return  InternalApiResponse.<List<LaborantResponse>>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(laborantResponses)
                .build();

    }
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{language}/update/{laborantId}")
    public InternalApiResponse<LaborantResponse> updateLaborant(@PathVariable("language") Language language,
                                                                @PathVariable Long laborantId,
                                                                @RequestBody LaborantUpdateRequest laborantUpdateRequest){
        log.debug("[{}][UpdataLaborant] -> request{}",this.getClass().getSimpleName(),laborantId);
        Laborant laborant= laborantService.updateLaborant(language,laborantId,laborantUpdateRequest);
        LaborantResponse laborantResponse= LaborantResponse.builder().laborantId(laborant.getLaborantId()).laborantName(laborant.getLaborantName())
                .laborantSurName(laborant.getLaborantSurName()).hospitalIdNo(laborant.getHospitalIdNo())
                .role(roleService.getRole(language,laborant.getRole().getRoleId())).build();
        log.debug("[{}][UpdataLaborant] -> request{}",this.getClass().getSimpleName(),laborantResponse);

        return InternalApiResponse.<LaborantResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.LABORANT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(laborantResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{laborantId}")
    public  InternalApiResponse<LaborantResponse> deleteLaborant (@PathVariable("language") Language language, @PathVariable Long laborantId){
        log.debug("[{}][deleteLaborant] -> request{}",this.getClass().getSimpleName(),laborantId);
        if(laborantService.deleteLaborant(language,laborantId)){
            return InternalApiResponse.<LaborantResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.LABORANT_SUCCESSFULLY_DELETED))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();

        }else {
            return InternalApiResponse.<LaborantResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ERROR))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION))
                            .build())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .hasError(true)
                    .build();
        }

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getAll")
    public InternalApiResponse<List<LaborantResponse>> getAllLaborant(@PathVariable(name = "language")Language language){
        log.debug("[{}][AllLaborant] -> request{}",this.getClass().getSimpleName(),this.getClass().getSimpleName());
        List<Laborant> laborants= laborantService.getAllLaborants(language);
        List<LaborantResponse> laborantResponses= laborants.stream().map(arg ->LaborantResponse.builder().laborantId(arg.getLaborantId()).laborantName(arg.getLaborantName())
                .laborantSurName(arg.getLaborantSurName()).hospitalIdNo(arg.getHospitalIdNo())
                .userName(arg.getUserName())
                .role(Role.builder().roleId(arg.getRole().getRoleId()).userRole(arg.getRole().getUserRole()).build()).build()).collect(Collectors.toList());

        return InternalApiResponse.<List<LaborantResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(laborantResponses)
                .build();
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{language}/Login")
    public InternalApiResponse<LaborantResponse> Login(@PathVariable(name = "language")Language language,
                                                      String userName,
                                                      String password) {
        log.debug("[{}][Login] -> request{}", this.getClass().getSimpleName(), userName, password);
        Laborant laborant = laborantService.Login(language,  userName, password);
        System.out.println(laborant);
        LaborantResponse laborantResponse= LaborantResponse.builder().laborantId(laborant.getLaborantId()).laborantName(laborant.getLaborantName())
                .laborantSurName(laborant.getLaborantSurName()).hospitalIdNo(laborant.getHospitalIdNo())
                .role(roleService.getRole(language,laborant.getRole().getRoleId())).build();


        log.debug("[{}][Login] -> request{}",this.getClass().getSimpleName(),laborantResponse);;
        return InternalApiResponse.<LaborantResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.LABORANT_NOT_FOUND_EXCEPTION))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(laborantResponse)
                .build();

    }



}
