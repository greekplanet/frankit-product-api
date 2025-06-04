package com.frankit.product_api.application.controller.mock;

import com.frankit.product_api.application.dto.request.LoginRequest;
import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("mock")
@Tag(name = "Mock Auth API", description = "Mock 로그인 API")
@RestController
@RequestMapping("/api/auth")
public class AuthMockController {

    @Operation(summary = "Mock 로그인", description = "Mock JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> mockLogin(@RequestBody LoginRequest request) {
        LoginResponse response = LoginResponse.builder()
                        .token("eyJhbGciOiJIUzI1NiJ9...")
                        .build();

        return ApiResponse.<LoginResponse>builder()
                .status(201)
                .success(true)
                .data(response)
                .build();
    }
}
