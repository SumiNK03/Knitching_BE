package com.douzone.knitching.domain.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "수강신청 커리큘럼 아이템 응답 DTO")
public class EnrollmentItemResponseDTO {

    @JsonProperty("user_progress_id")
    @Schema(description = "사용자 진도 ID", example = "21")
    private Long userProgressId;

    @JsonProperty("video_key")
    @Schema(description = "유튜브 키", example = "dQw4w9WgXcQ")
    private String videoKey;

    @JsonProperty("seq")
    @Schema(description = "조회 순번", example = "1")
    private Integer seq;

    @JsonProperty("title")
    @Schema(description = "강좌 제목", example = "코잡기 기초")
    private String title;

    @JsonProperty("is_completed")
    @Schema(description = "수강 완료 여부", example = "true")
    private Boolean isCompleted;
}
