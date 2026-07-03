package com.loopers.domain.coupon;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * 스트리머 측 user_coupon 매핑. 발급 성공 시 이 엔티티를 저장(insert)한다.
 * status는 commerce-api의 UserCouponStatus와 같은 문자열("AVAILABLE")을 저장한다.
 */
@Entity
@Table(name = "user_coupon")
public class UserCouponModel extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(nullable = false, length = 10)
    private String status;

    @Version
    @Column(nullable = false)
    private Long version;

    protected UserCouponModel() {}

    public UserCouponModel(Long userId, Long couponId) {
        this.userId = userId;
        this.couponId = couponId;
        this.status = "AVAILABLE";
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public String getStatus() {
        return status;
    }
}
