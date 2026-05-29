package com.loopers.application.product;

import com.loopers.domain.brand.Brand;
import com.loopers.domain.brand.BrandService;
import com.loopers.domain.product.Product;
import com.loopers.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductFacade {
    private final ProductService productService;
    private final BrandService brandService;

    public ProductInfo createProduct(Long brandId, String name, String description, Long price, Integer stock) {
        Product product = productService.createProduct(brandId, name, description, price, stock);
        return ProductInfo.from(product);
    }

    public ProductInfo getProduct(Long id) {
        Product product = productService.getProduct(id);
        return ProductInfo.from(product);
    }

    public ProductDetailInfo getProductDetail(Long productId) {
        Product product = productService.getProduct(productId);
        Brand brand = brandService.getBrand(product.getBrandId());
        return ProductDetailInfo.of(product, brand);
    }

    public List<ProductInfo> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream()
            .map(ProductInfo::from)
            .toList();
    }

    public ProductInfo updateProduct(Long id, Long brandId, String name, String description, Long price, Integer stock) {
        Product product = productService.updateProduct(id, brandId, name, description, price, stock);
        return ProductInfo.from(product);
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }
}
