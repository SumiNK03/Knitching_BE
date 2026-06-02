package com.douzone.knitching.domain.pattern.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

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

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "등록 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
