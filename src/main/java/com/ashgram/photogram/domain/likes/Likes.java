package com.ashgram.photogram.domain.likes;

import com.ashgram.photogram.domain.image.Image;
import com.ashgram.photogram.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(
        uniqueConstraints = { // unique 제약조건을 여러 개 걸기 위함 -> 같은 유저가 하나의 이미지에 중복으로 좋아요 불가
                @UniqueConstraint(
                        name = "likes_uk",
                        columnNames = {"imageId", "userId"} // 실제 DB 테이블의 컬럼 명
                )
        }
)
public class Likes { // N
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "imageId")
    private Image image; // 1 -> 하나의 이미지, 여러 개의 좋아요

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({"images"})
    private User user; // 1 -> 하나의 유저, 여러 개의 좋아요
    
    private LocalDateTime createDate;

    @PrePersist // nativeQuery를 사용하면, 동작하지 않으므로 nativeQuery 안에 직접 now()를 넣어줘야 함
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
