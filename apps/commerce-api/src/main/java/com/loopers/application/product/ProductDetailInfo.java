package com.loopers.application.product;

import com.loopers.domain.brand.Brand;
import com.loopers.domain.product.Product;

public record ProductDetailInfo(
    Long id,
    String name,
    String description,
    Long price,
    Integer stock,
    BrandInfo brand
) {
    public record BrandInfo(
        Long id,
        String name,
        String logoUrl
    ) {
        public static BrandInfo from(Brand brand) {
            return new BrandInfo(
                brand.getId(),
                brand.getName(),
                brand.getLogoUrl()
            );
        }
    }

    public static ProductDetailInfo of(Product product, Brand brand) {
        return new ProductDetailInfo(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            BrandInfo.from(brand)
        );
    }
}
