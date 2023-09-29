package com.example.ozguryazilimproje.controller;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.request.RoleCreateRequest;
import com.example.ozguryazilimproje.response.FriendlyMessage;
import com.example.ozguryazilimproje.response.InternalApiResponse;
import com.example.ozguryazilimproje.response.RoleResponse;
import com.example.ozguryazilimproje.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/role")
@RequiredArgsConstructor
@CrossOrigin
public class RoleController {
    private  final RoleService roleService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{language}/create")
    public InternalApiResponse<RoleResponse> createRole(@PathVariable(name = "language")Language language,
                                                        @RequestBody RoleCreateRequest roleCreateRequest){
        log.debug("[{}][CreateRole] -> request{}",this.getClass().getSimpleName(),roleCreateRequest);
        Role role = roleService.createRole(language,roleCreateRequest);
        RoleResponse roleResponse = RoleResponse.builder()
                .roleId(role.getRoleId())
                .userRole(role.getUserRole())
                .build();
        log.debug("[{}][CreateRole] -> request{}",this.getClass().getSimpleName(),roleResponse);
        return InternalApiResponse.<RoleResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ROLE_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(roleResponse)
                .build();


    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getAll")
    public InternalApiResponse<List<RoleResponse>> getRoles (@PathVariable(name = "language")Language language){
        log.debug("[{}][LisRole] -> request{}",this.getClass().getSimpleName(),this.getClass().getSimpleName());
        List<Role> roles= roleService.getRoles(language);
        List<RoleResponse> roleResponses= roles.stream().map(arg -> RoleResponse.builder().roleId(arg.getRoleId())
                .userRole(arg.getUserRole())
                .build()).collect(Collectors.toList());
        log.debug("[{}][LisRole] -> request{}",this.getClass().getSimpleName(),roleResponses);
        return InternalApiResponse.<List<RoleResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(roleResponses)
                .build();

    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{language}/delete/{roleId}")
    public  InternalApiResponse<RoleResponse> deleteRole(@PathVariable(name = "language")Language language,
                                                         @PathVariable (name="roleId")Long roleId ){
        log.debug("[{}][DeleteRole] -> request{}",this.getClass().getSimpleName(),roleId);

        if (roleService.deleteRole(language,roleId) ){
            return InternalApiResponse.<RoleResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ROLE_SUCCESSFULLY_DELETED))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();
        }else {
            return InternalApiResponse.<RoleResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ERROR))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ROLE_NOT_DELETED_EXCEPTION))
                            .build())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .hasError(true)
                    .build();
        }


    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/get/{roleId}")
    public  InternalApiResponse<RoleResponse> getRole(@PathVariable(name = "language")Language language,
                                                         @PathVariable (name="roleId")Long roleId ){
        log.debug("[{}][DeleteRole] -> request{}",this.getClass().getSimpleName(),roleId);
        if (roleService.getRole(language,roleId) !=null){
            return InternalApiResponse.<RoleResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();
        }else {
            return InternalApiResponse.<RoleResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ERROR))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION))
                            .build())
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .hasError(true)
                    .build();
        }


    }








}
