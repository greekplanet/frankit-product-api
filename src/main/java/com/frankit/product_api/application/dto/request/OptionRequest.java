package com.frankit.productapi.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "옵션 등록 요청 DTO")
public class OptionRequest {

    @NotBlank
    @Schema(description = "옵션 이름", example = "색상")
    private String name;

    @NotBlank
    @Schema(description = "옵션 타입", example = "SELECT")
    private String type;

    @NotEmpty
    @Schema(description = "옵션 값 목록", example = "[\"빨강\", \"파랑\"]")
    private List<String> values;

    @Positive
    @Schema(description = "추가 금액", example = "1000")
    private double additionalPrice;
}