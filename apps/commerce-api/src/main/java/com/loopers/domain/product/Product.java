package com.loopers.domain.product;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

    @Column(name = "brand_id", nullable = false)
    private Long brandId;
    private String name;
    private String description;
    private Long price;
    private Integer stock;

    @Builder
    public Product(Long brandId, String name, String description, Long price, Integer stock) {
        validate(brandId, name, description, price, stock);

        this.brandId = brandId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public void update(Long newBrandId, String newName, String newDescription, Long newPrice, Integer newStock) {
        validate(newBrandId, newName, newDescription, newPrice, newStock);

        this.brandId = newBrandId;
        this.name = newName;
        this.description = newDescription;
        this.price = newPrice;
        this.stock = newStock;
    }

    private void validate(Long brandId, String name, String description, Long price, Integer stock) {
        if (brandId == null) {
            throw new CoreException(ErrorType.BAD_REQUEST, "브랜드는 필수입니다.");
        }
        if (name == null || name.isBlank()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "상품명은 비어있을 수 없습니다.");
        }
        if (description == null || description.isBlank()) {
            throw new CoreException(ErrorType.BAD_REQUEST, "상품 설명은 비어있을 수 없습니다.");
        }
        if (price == null || price < 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "가격은 0 이상이어야 합니다.");
        }
        if (stock == null || stock < 0) {
            throw new CoreException(ErrorType.BAD_REQUEST, "재고는 0 이상이어야 합니다.");
        }
    }
}
