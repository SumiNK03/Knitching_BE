package com.douzone.knitching.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 기술 수정 요청 아이템")
public class UserSkillUpdateItemDTO {
    
    @NotBlank(message = "기술 코드는 필수입니다")
    @Schema(description = "기술 식별 코드", example = "JAVA_001")
    private String techCode;
    
    @Min(value = 0, message = "기술 숙련도는 0 이상이어야 합니다")
    @Max(value = 1, message = "기술 숙련도는 1 이하여야 합니다")
    @Schema(description = "기술 숙련도 (0: 불가능, 1: 가능)", example = "1")
    private Integer level;
}
