package com.douzone.knitching.domain.curriculum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.video.entity.Video;

@Entity
@Table(name = "curriculum_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "커리큐럼 학습 단계 정보")
public class CurriculumItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ITEM_ID")
    @Schema(description = "문항 고유 번호")
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "CURRI_ID", nullable = false)
    @Schema(description = "소속 커리큐럼")
    private Curriculum curriculum;

    @Column(name = "STEP_ORDER", nullable = false)
    @Schema(description = "학습 순서")
    private Integer stepOrder;

    @Column(name = "TITLE", nullable = false, length = 100)
    @Schema(description = "단계 제목")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    @Schema(description = "단계 상세 내용")
    private String description;

    @Column(name = "TECH_CODE", length = 20)
    @Schema(description = "기술 영상 코드")
    private String techCode;

    @ManyToOne
    @JoinColumn(name = "TECH_CODE", referencedColumnName = "TECH_CODE", insertable = false, updatable = false)
    @Schema(description = "기술 영상 정보")
    private Video video;
}
