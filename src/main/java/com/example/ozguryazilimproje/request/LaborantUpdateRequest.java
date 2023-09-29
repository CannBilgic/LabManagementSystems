package com.example.ozguryazilimproje.request;

import lombok.Data;

@Data
public class LaborantUpdateRequest {
    private String laborantName;
    private String laborantSurName;
    private String password;
    private String hospitalIdNo;
    private  String userName;
    private RoleGetRequest roleId;
}
