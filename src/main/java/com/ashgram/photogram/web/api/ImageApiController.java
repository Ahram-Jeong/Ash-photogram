package com.ashgram.photogram.web.api;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.image.Image;
import com.ashgram.photogram.service.ImageService;
import com.ashgram.photogram.service.LikesService;
import com.ashgram.photogram.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ImageApiController {
    private final ImageService imageService;
    private final LikesService likesService;

    // ******************** 포토그램 피드 ********************
    @GetMapping("/api/image")
    public ResponseEntity<?> imageFeed(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                       @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Image> images = imageService.imageFeed(principalDetails.getUser().getId(), pageable);
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
    }
    
    // ******************** 좋아요 ********************
    @PostMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> likes(@PathVariable long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.mlike(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
    }

    // ******************** 좋아요 취소 ********************
    @DeleteMapping("/api/image/{imageId}/likes")
    public ResponseEntity<?> unlikes(@PathVariable long imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        likesService.mUnLike(imageId, principalDetails.getUser().getId());
        return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소", null), HttpStatus.OK);
    }
}
