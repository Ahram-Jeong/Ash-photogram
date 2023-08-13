package com.ashgram.photogram.domain.user;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 DB를 따라감
    private long id;

    @Column(unique = true, length = 20)
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

    private LocalDateTime createDate;

    @PrePersist // DB에 insert 하기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
