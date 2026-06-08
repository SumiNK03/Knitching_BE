package com.douzone.knitching.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 기술 정보 응답 DTO")
public class UserSkillResponseDTO {
    
    @Schema(description = "기술 식별 코드", example = "JAVA_001")
    private String techCode;
    
    @Schema(description = "기술 제목", example = "변수와 데이터 타입")
    private String title;
    
    @Schema(description = "기술 숙련도 (0: 불가능, 1: 가능)", example = "0")
    private Integer level;
    
    @Schema(description = "최종 업데이트 일시", example = "2026-06-08T10:30:00")
    private String updatedAt;
}
