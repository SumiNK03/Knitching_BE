package com.douzone.knitching.domain.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "수강신청 조회 응답 DTO")
public class EnrollmentListResponseDTO {

    @JsonProperty("enrollment_id")
    @Schema(description = "수강신청 ID", example = "15")
    private Long enrollmentId;

    @JsonProperty("curriculum_id")
    @Schema(description = "커리큘럼 ID", example = "3")
    private Long curriculumId;

    @JsonProperty("pattern_name")
    @Schema(description = "패턴 이름", example = "초보자용 토끼 인형")
    private String patternName;

    @JsonProperty("author_name")
    @Schema(description = "작가 이름", example = "수민작가")
    private String authorName;

    @JsonProperty("tool")
    @Schema(description = "사용 도구", example = "대바늘")
    private String tool;

    @JsonProperty("thumbnail_url")
    @Schema(description = "패턴 대표 이미지 URL", example = "https://example.com/thumb.jpg")
    private String thumbnailUrl;

    @JsonProperty("total_items")
    @Schema(description = "커리큘럼 전체 아이템 수", example = "10")
    private Integer totalItems;

    @JsonProperty("completed_items")
    @Schema(description = "완료된 아이템 수", example = "4")
    private Integer completedItems;

    @JsonProperty("items")
    @Schema(description = "커리큘럼 아이템 목록")
    private List<EnrollmentItemResponseDTO> items;
}
