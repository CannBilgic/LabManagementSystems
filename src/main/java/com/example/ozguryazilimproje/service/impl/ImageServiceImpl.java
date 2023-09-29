package com.example.ozguryazilimproje.service.impl;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.exceptions.ImageNotSaveException;
import com.example.ozguryazilimproje.exception.exceptions.ReportNotFoundException;
import com.example.ozguryazilimproje.repository.entity.Image;
import com.example.ozguryazilimproje.repository.entity.Report;
import com.example.ozguryazilimproje.repository.repository.ImageRepository;
import com.example.ozguryazilimproje.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    @Override
    public Image saveImage(Language language, MultipartFile multipartFile) {
        log.debug("[{}][SaveImage] -> request{}",this.getClass().getSimpleName());
        try {
            Image image = Image.builder()
                    .name(multipartFile.getOriginalFilename())
                    .type(multipartFile.getContentType())
                    .file(multipartFile.getBytes())
                    .build();
            Image imagaResponse = imageRepository.save(image);
            log.debug("[{}][SaveImage] -> request{}",this.getClass().getSimpleName(),imagaResponse);
            return imagaResponse;

        }catch (Exception exception){
            throw new ImageNotSaveException(language, FriendlyMessageCodes.IMAGE_NOT_SAVE_EXCEPTION,"Image request:");
        }
    }

    @Override
    public Optional<Image> getFile(Language language,Long id) {

        return imageRepository.findById(id);

    }

    @Override
    public boolean deleteImage(Language language, Long imageId) {
        log.debug("[{}][GetImage] -> request{}",this.getClass().getSimpleName(),imageId);
        try {
            imageRepository.deleteById(imageId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public List<Image> getAll(Language language) {
        log.debug("[{}][getAllImage] -> request{}",this.getClass().getSimpleName());
        List<Image> images= imageRepository.findAll();
        if (images.isEmpty()){
            throw  new ReportNotFoundException(language,FriendlyMessageCodes.IMAGE_NOT_FOUND_EXCEPTION,"Image Not Found");
        }
        log.debug("[{}][getAllReport] -> request{}",this.getClass().getSimpleName(),images);
        return images;
    }
}
