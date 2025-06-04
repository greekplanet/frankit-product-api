package com.frankit.product_api.domain.option;

import java.util.List;
import java.util.Optional;

public interface OptionRepository {
    Option save(Option option);
    List<Option> findByProductId(Long productId);
    Optional<Option> findById(Long id);
    void deleteById(Long id);
}
