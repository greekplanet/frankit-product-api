package com.frankit.product_api.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "회원가입 응답 DTO")
public class RegisterResponse {

    @Schema(description = "등록된 사용자 이메일", example = "user@example.com")
    private String email;

    @Schema(description = "등록 성공 메시지", example = "회원가입이 성공적으로 완료되었습니다.")
    private String message;
}