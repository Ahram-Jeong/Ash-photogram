package com.ashgram.photogram.web.api;

import com.ashgram.photogram.config.auth.PrincipalDetails;
import com.ashgram.photogram.domain.comment.Comment;
import com.ashgram.photogram.service.CommentService;
import com.ashgram.photogram.web.dto.CMRespDto;
import com.ashgram.photogram.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;

    // ******************** 댓글 등록 ********************
    @PostMapping("/api/comment")
    public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto, BindingResult bindingResult, // 유효성 검사, BindingResul는 꼭 @Valid 다음 파라미터에 와야 함
                                         @AuthenticationPrincipal PrincipalDetails principalDetails) { // 객체를 json 형식으로 받으려면 @RequestBody 필요
        // 유효성 검사 AOP 실행 -> ValidationAdvice
        
        Comment comment = commentService.registComment(commentDto.getContent(), commentDto.getImageId(), principalDetails.getUser().getId()); // imageId, content, userId
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 등록 성공", comment), HttpStatus.CREATED);
    }

    // ******************** 댓글 삭제 ********************
    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<?> commentDelete(@PathVariable long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "댓글 삭제 성공", null), HttpStatus.OK);
    }
}
