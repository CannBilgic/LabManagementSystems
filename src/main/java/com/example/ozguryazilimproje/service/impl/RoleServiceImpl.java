package com.example.ozguryazilimproje.service.impl;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.exceptions.RoleNotCreatedException;
import com.example.ozguryazilimproje.exception.exceptions.RoleNotFoundException;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.repository.repository.RoleRepository;
import com.example.ozguryazilimproje.request.RoleCreateRequest;
import com.example.ozguryazilimproje.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;


    @Override
    public Role createRole(Language language, RoleCreateRequest roleCreateRequest) {
        log.debug("[{}][CreateRole] -> request{}",this.getClass().getSimpleName(),roleCreateRequest);
        try {
            Role role= Role.builder()
                    .userRole(roleCreateRequest.getUserRole())
                    .build();
            Role roleResponse = roleRepository.save(role);
            log.debug("[{}][CreateRole] -> request{}",this.getClass().getSimpleName(),roleResponse);
            return roleResponse;
        }catch (Exception exception){
            throw  new RoleNotCreatedException(language, FriendlyMessageCodes.ROLE_NOT_CREATED_EXCEPTION,"role request:"+roleCreateRequest.toString());
        }
    }

    @Override
    public List<Role> getRoles(Language language) {
        log.debug("[{}][ListRole] -> request{}",this.getClass().getSimpleName(),this.getClass().getSimpleName());
        List<Role> roles=roleRepository.findAll();
        if(roles.isEmpty()){
            throw new RoleNotFoundException(language,FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION,"role not found");
        }
        log.debug("[{}][ListRole] -> request{}",this.getClass().getSimpleName(),roles);

        return  roles;
    }

    @Override
    public Role getRole(Language language, Long roleId) {
        log.debug("[{}][getRole] -> request{}",this.getClass().getSimpleName(),roleId);
        try {
            Role role= roleRepository.findById(roleId).get();
            return role;
        }catch (Exception exception){
            throw new RoleNotFoundException(language,FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION,"role not found");
        }

    }

    @Override
    public boolean deleteRole(Language language, Long roleId) {
        log.debug("[{}][DeleteRole] -> request{}",this.getClass().getSimpleName(),this.getClass().getSimpleName());

        if (roleRepository.findById(roleId).isEmpty()){
            System.out.println("21321321312");
            throw new RoleNotFoundException(language,FriendlyMessageCodes.ROLE_NOT_FOUND_EXCEPTION,"role not found");
        }else {
            roleRepository.deleteById(roleId);
            return true;
        }


    }
}
