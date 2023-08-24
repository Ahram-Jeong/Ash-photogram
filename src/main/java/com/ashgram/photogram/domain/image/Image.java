package com.ashgram.photogram.domain.image;

import com.ashgram.photogram.domain.comment.Comment;
import com.ashgram.photogram.domain.likes.Likes;
import com.ashgram.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image { // N, 1
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String caption; // 게시글 내용
    private String postImageUrl; // 업로드 된 사진을 서버의 특정 폴더에 저장 후, 그 저장 경로를 DB에 INSERT (사진 -> 서버, 경로 -> DB)

    @ManyToOne // -> 연관관계 주인
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"images"}) // 포토그램 피드에서 이미지 select 시, 무한 참조 방지를 위해 User 객체 내부의 images를 무시하고 JSON으로 파싱
    private User user; // 1, 1

    // 좋아요 기능
    @OneToMany(mappedBy = "image") // mappedBy = Likes의 Image 변수명
    @JsonIgnoreProperties({"image"}) // Image -> Likes 호출 시, Likes의 image를 무한 참조하는 것을 방지
    private List<Likes> likes;
    @Transient // DB에 컬럼 생성 안되게 하는 어노테이션
    private boolean likeState;
    @Transient
    private long likeCount;

    // 댓글 기능
    @OneToMany(mappedBy = "image") // 하나의 이미지에 여러 개의 댓글
    @OrderBy("id DESC")
    @JsonIgnoreProperties({"image"})
    private List<Comment> comments;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
