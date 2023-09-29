package com.example.ozguryazilimproje.response;

import com.example.ozguryazilimproje.repository.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class LaborantResponse {
    private Long laborantId;
    private String laborantName;
    private String laborantSurName;
    private String hospitalIdNo;
    private String userName;

    private Role role;
}
