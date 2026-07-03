package com.loopers.domain.coupon;

import com.loopers.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 스트리머 측 coupon_stock 매핑. 재고 차감은 네이티브 조건부 UPDATE로 수행하므로
 * 이 엔티티는 JpaRepository 타입 지정 및 (필요 시) 조회용으로만 쓴다.
 * 테이블 소유·생성은 commerce-api의 CouponStockModel과 공유한다(같은 DB).
 */
@Entity
@Table(name = "coupon_stock")
public class CouponStockModel extends BaseEntity {

    @Column(name = "coupon_id", nullable = false, unique = true)
    private Long couponId;

    @Column(nullable = false)
    private int quota;

    @Column(nullable = false)
    private int issued;

    protected CouponStockModel() {}

    public Long getCouponId() {
        return couponId;
    }

    public int getQuota() {
        return quota;
    }

    public int getIssued() {
        return issued;
    }
}
