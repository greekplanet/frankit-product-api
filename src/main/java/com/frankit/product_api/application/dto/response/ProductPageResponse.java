package com.frankit.product_api.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "상품 페이지 응답 DTO")
public class ProductPageResponse {

    @Schema(description = "상품 목록", example = "[{\"id\": 1, \"name\": \"노트북\", \"description\": \"고성능 노트북\", \"price\": 1500000, \"shippingFee\": 3000, \"createdAt\": \"2025-06-04T00:37:59.609773\"}]")
    private List<ProductResponse> content;

    @Schema(description = "페이지 정보")
    private PageInfo page;

    @Getter
    @Builder
    @Schema(description = "페이지 정보")
    public static class PageInfo {
        @Schema(description = "현재 페이지 번호", example = "0")
        private int number;

        @Schema(description = "페이지 크기", example = "10")
        private int size;

        @Schema(description = "전체 요소 수", example = "1")
        private long totalElements;

        @Schema(description = "전체 페이지 수", example = "1")
        private int totalPages;
    }
}