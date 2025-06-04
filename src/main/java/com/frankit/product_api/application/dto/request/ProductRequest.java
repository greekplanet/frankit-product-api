package com.frankit.product_api.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "상품 등록 요청 DTO")
public class ProductRequest {

    @NotBlank
    @Schema(description = "상품 이름", example = "노트북")
    private String name;

    @NotBlank
    @Schema(description = "상품 설명", example = "고성능 노트북")
    private String description;

    @Positive
    @Schema(description = "상품 가격", example = "1500000")
    private double price;

    @Positive
    @Schema(description = "배송비", example = "3000")
    private double shippingFee;
}