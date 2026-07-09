package com.loopers.infrastructure.queue;

import com.loopers.domain.queue.WaitingQueueRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisWaitingQueueRepository implements WaitingQueueRepository {

    private static final String KEY = "waiting-queue";

    // 순번 정확성이 핵심이라 복제 지연이 없는 master 템플릿으로 읽기까지 통일한다.
    private final ZSetOperations<String, String> zSet;

    public RedisWaitingQueueRepository(
        @Qualifier("redisTemplateMaster") RedisTemplate<String, String> redisTemplate
    ) {
        this.zSet = redisTemplate.opsForZSet();
    }

    @Override
    public boolean add(Long userId, double score) {
        // ZADD NX: 이미 있는 member면 score를 덮어쓰지 않아 기존 순번이 유지된다.
        Boolean added = zSet.addIfAbsent(KEY, member(userId), score);
        return Boolean.TRUE.equals(added);
    }

    @Override
    public Optional<Long> rank(Long userId) {
        return Optional.ofNullable(zSet.rank(KEY, member(userId)));
    }

    @Override
    public long size() {
        Long size = zSet.zCard(KEY);
        return size == null ? 0L : size;
    }

    private String member(Long userId) {
        return String.valueOf(userId);
    }
}
