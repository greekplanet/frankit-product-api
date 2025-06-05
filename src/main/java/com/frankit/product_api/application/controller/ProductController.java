package com.frankit.product_api.application.controller;

import com.frankit.product_api.application.dto.request.ProductRequest;
import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.ProductPageResponse;
import com.frankit.product_api.application.dto.response.ProductResponse;
import com.frankit.product_api.domain.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Tag(name = "Product API", description = "상품 관리 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록.")
    @PostMapping
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ApiResponse.<ProductResponse>builder()
                .status(201)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "상품 수정", description = "기존 상품을 수정.")
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제.")
    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteProduct(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<Object>builder()
                .status(200)
                .success(true)
                .data(Collections.singletonMap("message", "상품이 삭제되었습니다"))
                .build();
    }

    @Operation(summary = "상품 단건 조회", description = "특정 상품을 조회.")
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProduct(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long id) {
        ProductResponse response = productService.getProduct(id);
        return ApiResponse.<ProductResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "상품 목록 조회", description = "페이징된 상품 목록을 조회.")
    @GetMapping
    public ApiResponse<ProductPageResponse> getProducts(
            @Parameter(description = "페이징 정보") Pageable pageable) {
        ProductPageResponse response = productService.getProducts(pageable);
        return ApiResponse.<ProductPageResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }
}
