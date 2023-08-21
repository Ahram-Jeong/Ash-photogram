package com.ashgram.photogram.web.api;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.image.Image;
import com.ashgram.photogram.service.ImageService;
import com.ashgram.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageApiController {
    private final ImageService imageService;

    // ******************** 포토그램 피드 ********************
    @GetMapping("/api/image")
    public ResponseEntity<?> imageFeed(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Image> images = imageService.imageFeed(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }
}
