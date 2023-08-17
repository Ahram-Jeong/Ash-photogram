package com.ashgram.photogram.domain.subscribe;

import com.ashgram.photogram.domain.user.User;
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
        uniqueConstraints = { // unique 제약조건을 여러 개 걸기 위함 -> 구독 주체와 대상이 중복으로 들어가면 안된다
                @UniqueConstraint(
                        name = "subscribe_uk",
                        columnNames = {"fromUserId", "toUserId"} // 실제 DB 테이블의 컬럼 명
                )
        }
)
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private User fromUser; // 구독 주체

    @ManyToOne
    @JoinColumn(name = "toUserId")
    private User toUser; // 구독 대상

    private LocalDateTime createDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
