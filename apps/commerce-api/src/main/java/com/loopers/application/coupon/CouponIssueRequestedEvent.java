package com.loopers.application.coupon;

/**
 * 선착순 쿠폰 "발급 요청" 메시지. API는 이 메시지를 Kafka로 발행만 하고 즉시 응답한다.
 * 실제 발급(수량 제한)은 커밋 3의 컨슈머가 수행한다.
 *
 * @param requestId 요청 추적용 ID (커밋 5 결과 확인, 커밋 4 멱등에 활용)
 * @param couponId  발급 대상 쿠폰 (파티션 키로도 사용)
 * @param userId    발급 요청 유저
 */
public record CouponIssueRequestedEvent(
    String requestId,
    Long couponId,
    Long userId
) {
}
