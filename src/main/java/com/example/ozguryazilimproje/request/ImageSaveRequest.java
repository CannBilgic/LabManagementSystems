package com.example.ozguryazilimproje.request;

import lombok.Data;

@Data
public class ImageSaveRequest {
    private String name;
    private String type;
    private byte[]  file;
}
