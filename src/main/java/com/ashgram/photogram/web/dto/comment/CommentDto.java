package com.ashgram.photogram.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    /**
     * @NotNull : null 체크
     * @NotEmpty : null, 빈 값 체크
     * @NotBlank : null, 빈 값, 빈 공백 체크
     */
    
    @NotBlank
    private String content;
    @NotNull
    private Long imageId;
}
