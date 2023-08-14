package com.ashgram.photogram.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ImageController {
    // ******************** 홈, 피드 ********************
    @GetMapping({"/", "/image/story"})
    public String story() {
        return "image/story";
    }

    // ******************** 인기글 ********************
    @GetMapping("/image/popular")
    public String popular() {
        return "image/popular";
    }

    // ******************** 업로드 ********************
    @GetMapping("/image/upload")
    public String upload() {
        return "image/upload";
    }
}
