package com.frankit.product_api.application.controller;

import com.frankit.product_api.application.dto.request.LoginRequest;
import com.frankit.product_api.application.dto.request.RegisterRequest;
import com.frankit.product_api.application.dto.response.ApiResponse;
import com.frankit.product_api.application.dto.response.LoginResponse;
import com.frankit.product_api.application.dto.response.RegisterResponse;
import com.frankit.product_api.domain.user.UserService;
import com.frankit.product_api.infrastructure.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Auth API", description = "로그인 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Operation(summary = "로그인", description = "이메일과 비밀번호로 JWT 토큰을 발급.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = jwtUtil.generateToken(authentication.getName());
            LoginResponse response = LoginResponse.builder().token(token).build();
            return ApiResponse.<LoginResponse>builder()
                    .status(200)
                    .success(true)
                    .data(response)
                    .build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("인증 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
        }
    }

    @Operation(summary = "회원가입", description = "이메일과 비밀번호로 새로운 사용자를 등록.")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request.getEmail(), request.getPassword());
        RegisterResponse response = RegisterResponse.builder()
                .email(request.getEmail())
                .message("등록 완료: 회원가입이 성공적으로 완료되었습니다.")
                .build();
        return ResponseEntity.status(201).body(ApiResponse.<RegisterResponse>builder()
                .status(201)
                .success(true)
                .data(response)
                .build());
    }
}
