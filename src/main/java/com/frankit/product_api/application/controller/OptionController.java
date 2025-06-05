package com.frankit.product_api.application.controller;

import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.OptionResponse;
import com.frankit.product_api.domain.option.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.frankit.productapi.application.dto.request.OptionRequest;

import java.util.Collections;
import java.util.List;

@Tag(name = "Option API", description = "상품 옵션 관리 API")
@RestController
@RequestMapping("/api/products/{productId}/options")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;

    @Operation(summary = "옵션 등록", description = "상품에 새로운 옵션을 등록.")
    @PostMapping
    public ApiResponse<OptionResponse> createOption(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId,
            @Valid @RequestBody OptionRequest request) {
        OptionResponse response = optionService.createOption(productId, request);
        return ApiResponse.<OptionResponse>builder()
                .status(201)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "옵션 수정", description = "기존 옵션을 수정.")
    @PutMapping("/{optionId}")
    public ApiResponse<OptionResponse> updateOption(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "옵션 ID", example = "1") @PathVariable Long optionId,
            @Valid @RequestBody OptionRequest request) {
        OptionResponse response = optionService.updateOption(productId, optionId, request);
        return ApiResponse.<OptionResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "옵션 삭제", description = "옵션을 삭제.")
    @DeleteMapping("/{optionId}")
    public ApiResponse<Object> deleteOption(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "옵션 ID", example = "1") @PathVariable Long optionId) {
        optionService.deleteOption(productId, optionId);
        return ApiResponse.<Object>builder()
                .status(200)
                .success(true)
                .data(Collections.singletonMap("message", "옵션이 삭제되었습니다"))
                .build();
    }

    @Operation(summary = "옵션 목록 조회", description = "상품의 옵션 목록을 조회.")
    @GetMapping
    public ApiResponse<List<OptionResponse>> getOptions(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId) {
        List<OptionResponse> response = optionService.getOptions(productId);
        return ApiResponse.<List<OptionResponse>>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }
}
