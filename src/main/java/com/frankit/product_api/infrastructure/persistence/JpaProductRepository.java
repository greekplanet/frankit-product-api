package com.frankit.product_api.infrastructure.persistence;

import com.frankit.product_api.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {
}
