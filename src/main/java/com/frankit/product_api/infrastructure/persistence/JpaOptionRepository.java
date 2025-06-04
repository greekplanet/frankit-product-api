package com.frankit.product_api.infrastructure.persistence;

import com.frankit.product_api.domain.option.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByProductId(Long productId);
}
