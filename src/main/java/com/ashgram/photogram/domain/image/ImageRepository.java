package com.ashgram.photogram.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // ******************** 포토그램 피드 ********************
    @Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId)", nativeQuery = true)
    Page<Image> mFeed(@Param("principalId") long principalId, Pageable pageable);
}
