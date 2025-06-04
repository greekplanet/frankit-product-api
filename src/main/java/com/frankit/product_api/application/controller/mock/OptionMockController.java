package com.frankit.product_api.application.controller.mock;

import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.OptionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import com.frankit.productapi.application.dto.request.OptionRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Profile("mock")
@Tag(name = "Mock Option API", description = "Mock 옵션 관리 API")
@RestController
@RequestMapping("/api/products/{productId}/options")
public class OptionMockController {

    @Operation(summary = "Mock 옵션 등록", description = "Mock 옵션 데이터를 반환합니다.")
    @PostMapping
    public ApiResponse<OptionResponse> mockCreateOption(
            @PathVariable Long productId,
            @Valid @RequestBody OptionRequest request) {
        OptionResponse response = OptionResponse.builder()
                .id(1L)
                .name(request.getName())
                .type(request.getType())
                .values(request.getValues())
                .additionalPrice(request.getAdditionalPrice())
                .build();
        return ApiResponse.<OptionResponse>builder()
                .status(201)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "Mock 옵션 수정", description = "Mock 옵션 데이터를 수정하여 반환합니다.")
    @PutMapping("/{optionId}")
    public ApiResponse<OptionResponse> mockUpdateOption(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "옵션 ID", example = "1") @PathVariable Long optionId,
            @Valid @RequestBody OptionRequest request) {
        OptionResponse response = OptionResponse.builder()
                .id(optionId)
                .name(request.getName())
                .type(request.getType())
                .values(request.getValues())
                .additionalPrice(request.getAdditionalPrice())
                .build();
        return ApiResponse.<OptionResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }

    @Operation(summary = "Mock 옵션 삭제", description = "Mock 옵션 삭제 메시지를 반환합니다.")
    @DeleteMapping("/{optionId}")
    public ApiResponse<Map> mockDeleteOption(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId,
            @Parameter(description = "옵션 ID", example = "1") @PathVariable Long optionId) {
        return ApiResponse.<Map>builder()
                .status(200)
                .success(true)
                .data(Collections.singletonMap("message", "옵션이 삭제되었습니다"))
                .build();
    }

    @Operation(summary = "Mock 옵션 목록 조회", description = "Mock 옵션 목록을 반환합니다.")
    @GetMapping
    public ApiResponse<List<OptionResponse>> mockGetOptions(
            @Parameter(description = "상품 ID", example = "1") @PathVariable Long productId) {
        OptionResponse response = OptionResponse.builder()
                .id(1L)
                .name("색상")
                .type("SELECT")
                .values(List.of("빨강", "파랑"))
                .additionalPrice(1000)
                .build();
        return ApiResponse.<List<OptionResponse>>builder()
                .status(200)
                .success(true)
                .data(List.of(response))
                .build();
    }
}