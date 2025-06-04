package com.frankit.product_api.application.controller.mock;

import com.frankit.product_api.application.dto.request.ProductRequest;
import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.ProductPageResponse;
import com.frankit.product_api.application.dto.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Profile("mock")
@Tag(name = "Mock Product API", description = "Mock 상품 관리 API")
@RestController
@RequestMapping("/api/products")
public class ProductMockController {

    @Operation(summary = "Mock 상품 등록", description = "Mock 상품 데이터를 반환합니다.")
    @PostMapping
    public ApiResponse<ProductResponse> mockCreateProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .shippingFee(request.getShippingFee())
                .createdAt(LocalDateTime.now())
                .build();
        return ApiResponse.<ProductResponse>builder()
                .status(201)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "Mock 상품 수정", description = "Mock 상품 데이터를 수정하여 반환합니다.")
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> mockUpdateProduct(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = ProductResponse.builder()
                .id(id)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .shippingFee(request.getShippingFee())
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "Mock 상품 삭제", description = "Mock 상품 삭제 메시지를 반환합니다.")
    @DeleteMapping("/{id}")
    public ApiResponse<Map> mockDeleteProduct(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long id) {
        return ApiResponse.<Map>builder()
                .status(200)
                .success(true)
                .data(Collections.singletonMap("message", "상품이 삭제되었습니다"))
                .build();
    }

    @Operation(summary = "Mock 상품 단건 조회", description = "Mock 상품 데이터를 반환합니다.")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> mockGetProduct(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long id) {
        ProductResponse response = ProductResponse.builder()
                .id(id)
                .name("노트북")
                .description("고성능 노트북")
                .price(1500000)
                .shippingFee(3000)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "Mock 상품 목록 조회", description = "페이징된 Mock 상품 목록을 반환합니다.")
    @GetMapping
    public ApiResponse<ProductPageResponse> mockGetProducts(
            @Parameter(description = "페이징 정보") Pageable pageable) {
        ProductResponse product = ProductResponse.builder()
                .id(1L)
                .name("노트북")
                .description("고성능 노트북")
                .price(1500000)
                .shippingFee(3000)
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();

        ProductPageResponse response = ProductPageResponse.builder()
                .content(Collections.singletonList(product))
                .page(ProductPageResponse.PageInfo.builder()
                        .number(pageable.getPageNumber())
                        .size(pageable.getPageSize())
                        .totalElements(1)
                        .totalPages(1)
                        .build())
                .build();

        return ApiResponse.<ProductPageResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }
}