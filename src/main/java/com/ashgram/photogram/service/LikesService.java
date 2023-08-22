package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.likes.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    // ******************** 좋아요 ********************
    @Transactional
    public void mlike(long imageId, long principalId) {
        likesRepository.mLikes(imageId, principalId);
    }

    // ******************** 좋아요 취소 ********************
    @Transactional
    public void mUnLike(long imageId, long principalId) {
        likesRepository.mUnLikes(imageId, principalId);
    }
}
