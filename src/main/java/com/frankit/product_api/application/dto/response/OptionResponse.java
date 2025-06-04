package com.frankit.product_api.application.dto.response;

import com.frankit.product_api.domain.option.Option;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "옵션 응답 DTO")
public class OptionResponse {

    @Schema(description = "옵션 ID", example = "1")
    private Long id;

    @Schema(description = "옵션 이름", example = "색상")
    private String name;

    @Schema(description = "옵션 타입", example = "SELECT")
    private String type;

    @Schema(description = "옵션 값 목록", example = "[\"빨강\", \"파랑\"]")
    private List<String> values;

    @Schema(description = "추가 금액", example = "1000")
    private double additionalPrice;
}