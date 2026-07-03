package com.loopers.application.coupon;

import com.loopers.domain.coupon.UserCouponModel;
import com.loopers.infrastructure.coupon.CouponStockJpaRepository;
import com.loopers.infrastructure.coupon.UserCouponJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 발급 요청 1건을 처리한다. 조건부 UPDATE로 수량을 확보한 경우에만 실제 발급(user_coupon insert).
 * <p>
 * 처리 단위 = 요청 1건 = 트랜잭션 1개. (중복 발급 방지 멱등은 커밋 4에서 추가)
 */
@RequiredArgsConstructor
@Service
public class CouponIssueService {

    private final CouponStockJpaRepository couponStockJpaRepository;
    private final UserCouponJpaRepository userCouponJpaRepository;

    @Transactional
    public void issue(Long couponId, Long userId) {
        // 판단은 DB의 WHERE 절이 원자적으로 끝냈다. 여기선 그 결과(영향받은 행 수)를 확인만 한다.
        int updated = couponStockJpaRepository.tryIssue(couponId);
        if (updated == 0) {
            // 이미 소진 — 발급하지 않고 스킵(재시도/예외 없음)
            return;
        }
        userCouponJpaRepository.save(new UserCouponModel(userId, couponId));
    }
}
