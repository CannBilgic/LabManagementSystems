package com.example.ozguryazilimproje.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LaborantReportResponse {
    private Long laborantId;
    private String laborantName;
    private String laborantSurName;
    private String hospitalIdNo;
}
