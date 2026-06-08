package com.douzone.knitching.domain.curriculum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "학습 진도 완료 처리 응답 DTO")
public class CompleteUserProgressResponseDTO {

    @Schema(description = "진도 ID", example = "21")
    private Long progressId;

    @Schema(description = "수강 ID", example = "15")
    private Long enrollId;

    @Schema(description = "커리큘럼 아이템 ID", example = "8")
    private Long itemId;

    @Schema(description = "완료 여부", example = "true")
    private Boolean isCompleted;

    @Schema(description = "완료 시각", example = "2026-06-08T18:30:00")
    private String completedAt;

    @Schema(description = "수강 상태", example = "DONE")
    private String enrollmentStatus;
}