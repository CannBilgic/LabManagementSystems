package com.example.ozguryazilimproje.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RoleResponse {
    private Long roleId;
    private String userRole ;


}
