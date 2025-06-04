package com.frankit.product_api.domain.option;

import com.frankit.product_api.application.dto.response.OptionResponse;
import com.frankit.product_api.domain.product.Product;
import com.frankit.product_api.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.frankit.productapi.application.dto.request.OptionRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionService {

    private final OptionRepository optionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OptionResponse createOption(Long productId, OptionRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: ID " + productId));
        Option option = new Option();
        option.setName(request.getName());
        option.setType(OptionType.valueOf(request.getType()));
        option.setValues(request.getValues());
        option.setAdditionalPrice(request.getAdditionalPrice());
        product.addOption(option);
        Option saved = optionRepository.save(option);
        return mapToResponse(saved);
    }

    @Transactional
    public OptionResponse updateOption(Long productId, Long optionId, OptionRequest request) {
        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: ID " + productId));
        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new RuntimeException("옵션을 찾을 수 없습니다: ID " + optionId));
        option.setName(request.getName());
        option.setType(OptionType.valueOf(request.getType()));
        option.setValues(request.getValues());
        option.setAdditionalPrice(request.getAdditionalPrice());
        Option updated = optionRepository.save(option);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteOption(Long productId, Long optionId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: ID " + productId));
        if (!optionRepository.findById(optionId).isPresent()) {
            throw new RuntimeException("옵션을 찾을 수 없습니다: ID " + optionId);
        }
        optionRepository.deleteById(optionId);
    }

    @Transactional(readOnly = true)
    public List<OptionResponse> getOptions(Long productId) {
        productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: ID " + productId));
        List<Option> options = optionRepository.findByProductId(productId);
        return options.stream().map(this::mapToResponse).toList();
    }

    // entity -> 응답 dto로 변환
    private OptionResponse mapToResponse(Option option) {
        return OptionResponse.builder()
                .id(option.getId())
                .name(option.getName())
                .type(option.getType().name())
                .values(option.getValues())
                .additionalPrice(option.getAdditionalPrice())
                .build();
    }
}
