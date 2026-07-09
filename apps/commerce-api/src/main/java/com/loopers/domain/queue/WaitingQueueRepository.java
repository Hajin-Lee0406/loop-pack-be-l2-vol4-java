package com.loopers.domain.queue;

import java.util.Optional;

/**
 * 대기열 저장소. Redis Sorted Set으로 구현된다.
 * <ul>
 *   <li>score = 진입 시각(timestamp), member = userId</li>
 *   <li>중복 member는 Set 특성으로 자동 방지된다</li>
 * </ul>
 */
public interface WaitingQueueRepository {

    /**
     * 대기열에 진입시킨다. 이미 존재하는 유저면 score를 덮어쓰지 않고 무시한다(순번 유지).
     *
     * @return 신규 진입이면 true, 이미 있던 유저면 false
     */
    boolean add(Long userId, double score);

    /** 0-based 순번. 대기열에 없으면 empty. */
    Optional<Long> rank(Long userId);

    /** 현재 대기열 전체 인원. */
    long size();
}
