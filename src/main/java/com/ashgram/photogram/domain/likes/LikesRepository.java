package com.ashgram.photogram.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    // ******************** 좋아요 ********************
    @Modifying // INSERT, DELETE, UPDATE를 nativeQuery로 작성하기 위한 어노테이션
    @Query(value = "INSERT INTO likes (imageId, userId, createDate) VALUES (:imageId, :principalId, now())", nativeQuery = true)
    int mLikes(@Param("imageId") long imageId, @Param("principalId") long principalId);

    // ******************** 좋아요 취소 ********************
    @Modifying
    @Query(value = "DELETE FROM likes WHERE imageId = :imageId AND userId = :principalId", nativeQuery = true)
    int mUnLikes(@Param("imageId") long imageId, @Param("principalId") long principalId);
}
