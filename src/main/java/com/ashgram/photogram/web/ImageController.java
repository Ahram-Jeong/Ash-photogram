package com.ashgram.photogram.web;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.image.Image;
import com.ashgram.photogram.handler.ex.CustomValidationException;
import com.ashgram.photogram.service.ImageService;
import com.ashgram.photogram.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    // ******************** 홈, 피드 ********************
    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    // ******************** 인기글 ********************
    @GetMapping("/image/popular")
    public String popular(Model model) {
        List<Image> images = imageService.popularPic();
        model.addAttribute("images", images);
        return "image/popular";
    }

    // ******************** 업로드 ********************
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }

    @PostMapping("/image")
    public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(imageUploadDto.getFile().isEmpty()) {
            throw new CustomValidationException("이미지를 첨부하시기 바랍니다.", null);
        }
        
        imageService.picUpload(imageUploadDto, principalDetails);
        return "redirect:/user/"+ principalDetails.getUser().getId();
    }
}
