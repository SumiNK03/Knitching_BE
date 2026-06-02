package com.douzone.knitching.domain.video.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "기술 영상 정보")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIDEO_ID")
    @Schema(description = "영상 고유 번호")
    private Long videoId;

    @Column(name = "TECH_CODE", nullable = false, unique = true, length = 20)
    @Schema(description = "기술 식별 코드")
    private String techCode;

    @Column(name = "TITLE", nullable = false, length = 100)
    @Schema(description = "기술 제목")
    private String title;

    @Column(name = "VIDEO_KEY", nullable = false, length = 20)
    @Schema(description = "마루마이 영상 키")
    private String videoKey;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "등록 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
