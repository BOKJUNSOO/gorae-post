package com.gorae.gorae_post.api.open;

import com.gorae.gorae_post.common.dto.ApiResponseDto;
import com.gorae.gorae_post.service.aws.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/post/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping(value = "/image")
    public ApiResponseDto<String> imageLoad(@RequestPart(value = "image") MultipartFile multipartFile) throws IOException {
        String imageUrl = s3Service.uploadFile(multipartFile, "");
        return ApiResponseDto.createOk(imageUrl);
    }
}

