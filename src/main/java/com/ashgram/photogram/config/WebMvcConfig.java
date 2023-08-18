package com.ashgram.photogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.path}")
    private String uploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        // file:///C:/workspace/photogram/upload/
        registry
                .addResourceHandler("/upload/**") // jsp페이지에서 /upload/** 주소 패턴 요청 시
                .addResourceLocations("file:///" + uploadFolder)
                .setCachePeriod(60*10*6) // 1시간
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
}
