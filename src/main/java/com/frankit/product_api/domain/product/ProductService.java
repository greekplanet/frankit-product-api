package com.frankit.product_api.domain.product;

import com.frankit.product_api.application.dto.request.ProductRequest;
import com.frankit.product_api.application.dto.response.ProductPageResponse;
import com.frankit.product_api.application.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setShippingFee(request.getShippingFee());
        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: ID " + id));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setShippingFee(request.getShippingFee());
        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.findById(id).isPresent()) {
            throw new RuntimeException("상품을 찾을 수 없습니다: ID " + id);
        }
        productRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다: ID " + id));
        return mapToResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductPageResponse getProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return ProductPageResponse.builder()
                .content(products.getContent().stream().map(this::mapToResponse).toList())
                .page(ProductPageResponse.PageInfo.builder()
                        .number(products.getNumber())
                        .size(products.getSize())
                        .totalElements(products.getTotalElements())
                        .totalPages(products.getTotalPages())
                        .build())
                .build();
    }

    // entity -> 응답 dto로 변환
    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .shippingFee(product.getShippingFee())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
