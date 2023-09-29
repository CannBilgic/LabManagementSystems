package com.example.ozguryazilimproje.service;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.repository.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image saveImage(Language language, MultipartFile multipartFile);
    Optional<Image> getFile(Language language,Long imageId);
    boolean deleteImage(Language language, Long imageId);

    List<Image> getAll(Language language);



}
