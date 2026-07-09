package com.loopers.domain.queue;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class WaitingQueueService {

    private final WaitingQueueRepository waitingQueueRepository;

    public WaitingQueueService(WaitingQueueRepository waitingQueueRepository) {
        this.waitingQueueRepository = waitingQueueRepository;
    }

    /**
     * 대기열에 진입시키고 현재 순번을 반환한다.
     * 이미 진입한 유저면 순번을 유지한다(멱등).
     */
    public QueuePosition enter(Long userId) {
        waitingQueueRepository.add(userId, System.currentTimeMillis());
        return getPosition(userId);
    }

    /**
     * 현재 순번과 전체 대기 인원을 조회한다.
     *
     * @throws CoreException 대기열에 없는 유저면 NOT_FOUND
     */
    public QueuePosition getPosition(Long userId) {
        long rank = waitingQueueRepository.rank(userId)
            .orElseThrow(() -> new CoreException(ErrorType.NOT_FOUND, "대기열에 없는 유저입니다."));
        long total = waitingQueueRepository.size();
        return new QueuePosition(rank, total);
    }
}
