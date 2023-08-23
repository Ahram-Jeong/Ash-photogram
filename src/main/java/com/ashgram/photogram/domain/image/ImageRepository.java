package com.ashgram.photogram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // ******************** 포토그램 피드 ********************
    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId)", nativeQuery = true)
    Page<Image> mFeed(@Param("principalId") long principalId, Pageable pageable);

    // ******************** 인기글 ********************
    @Query(value = "SELECT i.* FROM image i INNER JOIN (SELECT imageId, COUNT(imageId) likeCount FROM likes GROUP BY imageId) c ON i.id = c.imageId ORDER BY likeCount DESC", nativeQuery = true)
    List<Image> mPopular();
}
