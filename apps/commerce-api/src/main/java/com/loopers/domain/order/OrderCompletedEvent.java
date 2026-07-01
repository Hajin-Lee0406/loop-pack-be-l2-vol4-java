package com.loopers.domain.order;

/**
 * "주문이 완료되었다"는 사실을 나타내는 도메인 이벤트.
 * <p>
 * {@code OrderFacade.createOrder} 트랜잭션이 커밋된 뒤 발행된다. 주문 완료 알림, 유저 행동 로깅 등
 * 본 주문 로직과 분리돼야 하는 부가 관심사들이 이 사실을 각자 구독한다.
 */
public record OrderCompletedEvent(Long orderId, Long userId, Long finalPrice) {
}
