package com.frankit.product_api.application.controller;

import com.frankit.product_api.application.dto.request.LoginRequest;
import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.LoginResponse;
import com.frankit.product_api.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User API", description = "로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class AuthController {
    @Autowired
    UserService userService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 JWT 토큰을 발급.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ApiResponse.<LoginResponse>builder()
                .status(200)
                .success(true)
                .data(response)
                .build();
    }
}
