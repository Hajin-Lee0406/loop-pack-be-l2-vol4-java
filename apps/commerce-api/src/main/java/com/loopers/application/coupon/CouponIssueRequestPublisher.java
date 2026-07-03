package com.loopers.application.coupon;

import com.loopers.confg.kafka.Topics;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 발급 요청을 Kafka로 직행 발행한다(Outbox 미경유).
 * <p>
 * 범용 {@code kafkaTemplate}(key=String, value=JSON)을 사용한다. Outbox 전용 프로듀서와 달리
 * acks=all/idempotence가 없어 브로커 장애 시 요청이 유실될 수 있다(직행의 트레이드오프).
 */
@Component
public class CouponIssueRequestPublisher {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public CouponIssueRequestPublisher(
        @Qualifier("kafkaTemplate") KafkaTemplate<Object, Object> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(CouponIssueRequestedEvent event) {
        kafkaTemplate.send(Topics.COUPON_ISSUE_REQUESTS, event.couponId().toString(), event);
    }
}
