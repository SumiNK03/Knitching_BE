package com.douzone.knitching.domain.pattern.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "도안 목록 응답 DTO")
public class PatternListResponse {
    @Schema(description = "도안 고유 번호")
    private final Long patternId;

    @Schema(description = "도안 대표 이미지 경로")
    private final String thumbnailUrl;

    @Schema(description = "도안 이름")
    private final String patternName;

    @Schema(description = "도안 소유 작가 ID")
    private final Long patternAuthorId;

    @Schema(description = "도안 소유 작가 이름")
    private final String patternAuthorName;

    @Schema(description = "사용 도구")
    private final String tool;

    @Schema(description = "가격")
    private final Integer price;
}
