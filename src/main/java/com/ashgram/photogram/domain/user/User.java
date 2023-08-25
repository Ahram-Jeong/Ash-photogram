package com.ashgram.photogram.domain.user;

import com.ashgram.photogram.domain.image.Image;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 DB를 따라감
    private long id;

    @Column(unique = true, length = 100) // OAuth2 로그인으로 인한 길이 수정
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;

    private String website;
    private String bio; // 자기소개
    private String phone;
    private String gender;
    
    private String profileImageUrl; // 프로필 사진
    private String role; // 권한

    /**
     * 양방향 매핑
     * 연관관계의 주인이 아닐 경우, mappedBy 사용
     * FetchType.LAZY -> 지연 로딩 (함수로 호출 시 작동)
     * FetchType.EAGER -> 즉시 로딩 (join)
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // 무한 참조 방지를 위해 Image 객체 내부의 user를 무시하고 JSON으로 파싱
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist // DB에 insert 하기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
