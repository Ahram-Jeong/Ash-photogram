package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.comment.Comment;
import com.ashgram.photogram.domain.comment.CommentRepository;
import com.ashgram.photogram.domain.image.Image;
import com.ashgram.photogram.domain.user.User;
import com.ashgram.photogram.domain.user.UserRepository;
import com.ashgram.photogram.handler.ex.CustomApiException;
import com.ashgram.photogram.handler.ex.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // ******************** 댓글 등록 ********************
    @Transactional
    public Comment registComment(String content, long imageId, long userId) {
        // Tip : Image 객체 생성 시, id 값만 담아서 보내는 (insert 하는) 가상의 객체를 생성 -> id 값만 있고, 나머지는 빈 객체가 생성 됨
        // 어차피 Comment가 insert 될 때, 해당 객체의 FK인 id 값만 빼서 저장 하기 때문에 가능
        Image image = new Image();
        image.setId(imageId);

        User useEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("유저 ID 찾기 실패");
        });

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setImage(image);
        comment.setUser(useEntity);

        return commentRepository.save(comment);
    }

    // ******************** 댓글 삭제 ********************
    @Transactional
    public void deleteComment(long id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomApiException(e.getMessage());
        }
    }
}
