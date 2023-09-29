package com.example.ozguryazilimproje.controller;

import com.example.ozguryazilimproje.enums.Language;
import com.example.ozguryazilimproje.exception.enums.FriendlyMessageCodes;
import com.example.ozguryazilimproje.exception.utils.FriendlyMessageUtils;
import com.example.ozguryazilimproje.repository.entity.Image;
import com.example.ozguryazilimproje.repository.entity.Report;
import com.example.ozguryazilimproje.response.*;
import com.example.ozguryazilimproje.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/image")
@RequiredArgsConstructor
@CrossOrigin
public class ImageController {
    private final ImageService imageService;

    @PostMapping(path = "/{language}/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public InternalApiResponse<ImageResponse> saveImage(@PathVariable("language") Language language,@RequestParam("image") MultipartFile file){
        log.debug("[{}] [SaveImage] -> request:{}",this.getClass().getSimpleName());
        Image image = imageService.saveImage(language,file);
        ImageResponse imageResponse =convertImageResponse(image);
        log.debug("[{}] [SaveImage] -> request:{}",this.getClass().getSimpleName(),file);
        return InternalApiResponse.<ImageResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.IMAGE_SUCCESSFULLY_SAVED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(imageResponse)
                .build();
    }

    @GetMapping("/{language}/get/{imageId}")
    public ResponseEntity<byte[]> getFile(@PathVariable("language") Language language,@PathVariable Long imageId) {
        Optional<Image> imageEntityOptional = imageService.getFile(language, imageId);

        if (!imageEntityOptional.isPresent()) {
            return ResponseEntity.notFound()
                    .build();
        }

        Image images = imageEntityOptional.get();
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(images.getType()))
                .body(images.getFile());

    }

    @DeleteMapping("/{language}/delete/{imageId}")
    public  InternalApiResponse<ImageResponse> deleteImage (@PathVariable("language") Language language,@PathVariable Long imageId){


        if (imageService.deleteImage(language,imageId)){
            return InternalApiResponse.<ImageResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.SUCCESS))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.IMAGE_SUCCESSFULLY_DELETE))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(false)
                    .build();
        }else{
            return InternalApiResponse.<ImageResponse>builder()
                    .friendlyMessage(FriendlyMessage.builder()
                            .title(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.ERROR))
                            .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.IMAGE_NOT_FOUND_EXCEPTION))
                            .build())
                    .httpStatus(HttpStatus.OK)
                    .hasError(true)
                    .build();
        }

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{language}/getAll")
    public InternalApiResponse<List<ImageResponse>> getAllImage(@PathVariable(name = "language")Language language) {
        List<Image> images = imageService.getAll(language);
        List<ImageResponse> imageResponses = images.stream().map(args->ImageResponse.builder()
                .imageId(args.getImageId())
                .name(args.getName())
                .type(args.getType())
                .file(args.getFile())
                .build()).collect(Collectors.toList());
        return InternalApiResponse.<List<ImageResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(imageResponses)
                .build();
    }

    private static ImageResponse convertImageResponse(Image image) {
        return ImageResponse.builder()
                .imageId(image.getImageId())
                .name(image.getName())
                .type(image.getType())
                .file(image.getFile())
                .build();

    }


}
