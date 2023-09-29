package com.example.ozguryazilimproje.service;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.repository.entity.Role;
import com.example.ozguryazilimproje.request.RoleCreateRequest;

import java.util.List;

public interface RoleService  {

    Role createRole (Language language, RoleCreateRequest roleCreateRequest);
    List<Role> getRoles(Language language );

    Role getRole(Language language ,Long roleId);

    boolean deleteRole(Language language,Long roleId);




}
