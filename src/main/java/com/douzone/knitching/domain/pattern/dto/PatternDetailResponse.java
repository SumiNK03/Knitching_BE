package com.douzone.knitching.domain.pattern.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "도안 상세 응답 DTO")
public class PatternDetailResponse {
    @Schema(description = "도안 고유 번호")
    private final Long patternId;

    @Schema(description = "도안 대표 이미지 경로")
    private final String thumbnailUrl;

    @Schema(description = "도안 이름")
    private final String patternName;

    @Schema(description = "가격")
    private final Integer price;

    @Schema(description = "도안 소유 작가 ID")
    private final Long patternAuthorId;

    @Schema(description = "도안 소유 작가 이름")
    private final String patternAuthorName;

    @Schema(description = "작가 사용자 ID")
    private final Long patternAuthorUserId;

    @Schema(description = "작가 로그인 ID")
    private final String patternAuthorLoginId;

    @Schema(description = "작가 이메일")
    private final String patternAuthorEmail;

    @Schema(description = "난이도")
    private final Integer difficulty;

    @Schema(description = "사용 도구")
    private final String tool;

    @Schema(description = "권장 숙련도")
    private final String skillLevel;

    @Schema(description = "패턴 상세 설명")
    private final String patternContent;

    @Schema(description = "좋아요 수")
    private final Integer like;

    @Schema(description = "조회수")
    private final Integer viewCount;

    @Schema(description = "수강신청 수")
    private final Integer enrollCount;

    @Schema(description = "평점")
    private final BigDecimal rating;
}
