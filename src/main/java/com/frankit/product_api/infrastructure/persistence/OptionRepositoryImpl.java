package com.frankit.product_api.infrastructure.persistence;

import com.frankit.product_api.domain.option.Option;
import com.frankit.product_api.domain.option.OptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OptionRepositoryImpl implements OptionRepository {
    private final JpaOptionRepository jpaRepository;

    @Override
    public Option save(Option option) {
        return jpaRepository.save(option);
    }

    @Override
    public List<Option> findByProductId(Long productId) {
        return jpaRepository.findByProductId(productId);
    }

    @Override
    public Optional<Option> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
