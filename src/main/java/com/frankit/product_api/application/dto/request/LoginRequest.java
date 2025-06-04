package com.frankit.product_api.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "로그인 요청 DTO")
public class LoginRequest {

    @NotBlank
    @Email
    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호", example = "pass123")
    private String password;
}