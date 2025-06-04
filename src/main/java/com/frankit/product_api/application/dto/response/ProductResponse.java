package com.frankit.product_api.application.dto.response;

import com.frankit.product_api.domain.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "상품 응답 DTO")
public class ProductResponse {

    @Schema(description = "상품 ID", example = "1")
    private Long id;

    @Schema(description = "상품 이름", example = "노트북")
    private String name;

    @Schema(description = "상품 설명", example = "고성능 노트북")
    private String description;

    @Schema(description = "상품 가격", example = "1500000")
    private double price;

    @Schema(description = "배송비", example = "3000")
    private double shippingFee;

    @Schema(description = "등록일", example = "2025-06-04T20:13:00")
    private LocalDateTime createdAt;
}