package com.example.ozguryazilimproje.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ImageResponse {
    private Long imageId;
    private String name;
    private String type;
    private byte[]  file;
}
