package com.ashgram.photogram.service;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.image.Image;
import com.ashgram.photogram.domain.image.ImageRepository;
import com.ashgram.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageService {
    private final ImageRepository imageRepository;

    // ******************** 업로드 ********************
    @Value("${file.path}") // application.yml > file > path
    private String uploadFolder;

    @Transactional
    public void picUpload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID(); // 범용 고유 식별자, Universally Unique IDentifier
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        log.info("imageFileName = {}", imageFileName);
        Path imageFilePath = Paths.get(uploadFolder + imageFileName);

        // 통신, I/O -> 예외가 발생할 수 있음
        try {
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        imageRepository.save(image);
//        log.info("imageEntity = {}", imageEntity); // [imageEntity를 출력]하는 과정에서 Image객체 안의 User가 무한 참조되는 현상이 발생하여, Image 객체의 toString()에 user를 빼줘야 한다.
    }
}
