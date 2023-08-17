package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.subscribe.SubscribeRepository;
import com.ashgram.photogram.handler.ex.CustomApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    // ******************** 구독 ********************
    @Transactional
    public void follow(long fromUserId, long toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        } catch (Exception e) {
            throw new CustomApiException("이미 follow 했습니다.");
        }
    }

    // ******************** 구독취소 ********************
    @Transactional
    public void unFollow(long fromUserId, long toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
