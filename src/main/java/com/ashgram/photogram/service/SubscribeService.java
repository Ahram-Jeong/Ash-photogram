package com.ashgram.photogram.service;

import com.ashgram.photogram.domain.subscribe.SubscribeRepository;
import com.ashgram.photogram.handler.ex.CustomApiException;
import com.ashgram.photogram.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // 모든 Repository는 EntityManager의 구현체

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

    // ******************** 구독 정보 ********************
    @Transactional(readOnly = true)
    public List<SubscribeDto> subList(long id, long pageUserId) {
        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if((SELECT true FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if((? = u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId WHERE s.fromUserId = ?"); // 세미콜론 제외
        // 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, id)
                .setParameter(2, id)
                .setParameter(3, pageUserId);
        // 실행 (QLRM 라이브러리 필요, DTO에 DB 결과를 매핑하기 위함)
        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subList = result.list(query, SubscribeDto.class);
        return subList;
    }
}
