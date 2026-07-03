package com.loopers.application.coupon;

import com.loopers.domain.coupon.CouponModel;
import com.loopers.domain.coupon.CouponService;
import com.loopers.domain.coupon.UserCouponModel;
import com.loopers.domain.user.UserModel;
import com.loopers.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CouponFacade {

    private final UserService userService;
    private final CouponService couponService;
    private final CouponIssueRequestPublisher issueRequestPublisher;

    public UserCouponInfo issue(String loginId, String loginPw, Long couponId) {
        UserModel user = userService.getUser(loginId, loginPw);
        UserCouponModel userCoupon = couponService.issueCoupon(user.getId(), couponId);
        CouponModel coupon = couponService.getCoupon(couponId);

        return UserCouponInfo.from(userCoupon, coupon);
    }

    /**
     * 선착순 발급 "요청"을 접수한다. 여기서 발급하지 않고 Kafka로 발행만 한 뒤
     * 추적용 requestId 를 돌려준다(202 Accepted 성격). 실제 발급은 컨슈머가 수행.
     */
    public String requestIssue(String loginId, String loginPw, Long couponId) {
        UserModel user = userService.getUser(loginId, loginPw);
        String requestId = UUID.randomUUID().toString();
        issueRequestPublisher.publish(new CouponIssueRequestedEvent(requestId, couponId, user.getId()));
        return requestId;
    }

    public List<UserCouponInfo> getMyCoupons(String loginId, String loginPw) {
        UserModel user = userService.getUser(loginId, loginPw);
        List<UserCouponModel> userCoupons = couponService.getUserCoupons(user.getId());

        return userCoupons.stream()
            .map(uc -> UserCouponInfo.from(uc, couponService.getCoupon(uc.getCouponId())))
            .toList();
    }
}
