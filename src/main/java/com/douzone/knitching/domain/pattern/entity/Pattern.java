package com.douzone.knitching.domain.pattern.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patterns")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "도안 정보")
public class Pattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PATTERN_ID")
    @Schema(description = "도안 고유 번호")
    private Long patternId;

    @Column(name = "TITLE", nullable = false, length = 100)
    @Schema(description = "도안 이름")
    private String title;

    @Column(name = "S3_FILE_URL", nullable = false, length = 255)
    @Schema(description = "AWS S3 PDF 파일 경로")
    private String s3FileUrl;

    @Column(name = "THUMBNAIL_URL", length = 255)
    @Schema(description = "도안 대표 이미지 경로")
    private String thumbnailUrl;

    @Column(name = "INST_ID")
    @Schema(description = "도안 소유 작가 ID")
    private Long instId;

    @Column(name = "PRICE", nullable = false)
    @Schema(description = "가격")
    private Integer price;

    @Column(name = "DIFFICULTY", nullable = false)
    @Schema(description = "난이도 별점 (1~5)")
    private Integer difficulty;

    @Column(name = "TOOL", nullable = false, length = 100)
    @Schema(description = "사용 도구 (대바늘/코바늘 등)")
    private String tool;

    @Column(name = "SKILL_LEVEL", length = 100)
    @Schema(description = "권장 숙련도 (초급~중급 등)")
    private String skillLevel;

    @Column(name = "CONTENT", columnDefinition = "TEXT")
    @Schema(description = "패턴 상세 설명")
    private String content;

    @Column(name = "VIEW_COUNT", nullable = false)
    @Schema(description = "조회수")
    private Integer viewCount;

    @Column(name = "LIKE_COUNT", nullable = false)
    @Schema(description = "좋아요 수")
    private Integer likeCount;

    @Column(name = "ENROLL_COUNT", nullable = false)
    @Schema(description = "수강신청 수")
    private Integer enrollCount;

    @Column(name = "RATING", nullable = false, precision = 2, scale = 1)
    @Schema(description = "평점 (예: 4.1)")
    private BigDecimal rating;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "등록 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (price == null) price = 0;
        if (difficulty == null) difficulty = 1;
        if (tool == null) tool = "대바늘";
        if (viewCount == null) viewCount = 0;
        if (likeCount == null) likeCount = 0;
        if (enrollCount == null) enrollCount = 0;
        if (rating == null) rating = BigDecimal.ZERO.setScale(1);
    }
}
