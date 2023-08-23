package com.ashgram.photogram.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    // ******************** 구독하기 ********************
    @Modifying // INSERT, DELETE, UPDATE를 nativeQuery로 작성하기 위한 어노테이션
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    void mSubscribe(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    // ******************** 구독취소 ********************
    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    void mUnSubscribe(@Param("fromUserId") Long fromUserId, @Param("toUserId") Long toUserId);

    // ******************** 구독여부 ********************
    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :fromUserId AND toUserId = :toUserId", nativeQuery = true)
    int mSubscribeState(@Param("toUserId") long toUserId, @Param("fromUserId") long fromUserId);

    // ******************** 구독 counting ********************
    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE fromUserId = :fromUserId", nativeQuery = true)
    long mSubscribeCount(@Param("fromUserId") long fromUserId);
}
