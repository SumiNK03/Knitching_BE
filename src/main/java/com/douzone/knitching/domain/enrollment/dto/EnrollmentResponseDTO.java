package com.douzone.knitching.domain.enrollment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "수강신청 응답 DTO")
public class EnrollmentResponseDTO {

    @Schema(description = "수강신청 고유 번호", example = "15")
    private Long enrollmentId;

    @Schema(description = "도안 고유 번호", example = "3")
    private Long patternId;

    @Schema(description = "도안 이름", example = "초보자용 토끼 인형")
    private String patternName;

    @Schema(description = "수강 상태", example = "PRE")
    private String status;

    @Schema(description = "수강신청 일시", example = "2026-06-08T18:30:00")
    private String enrolledAt;
}