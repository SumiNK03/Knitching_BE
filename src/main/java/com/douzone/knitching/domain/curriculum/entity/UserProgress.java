package com.douzone.knitching.domain.curriculum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_progress")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 학습 진도 정보")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROGRESS_ID")
    @Schema(description = "진도 기록 고유 번호")
    private Long progressId;

    @ManyToOne
    @JoinColumn(name = "ENROLL_ID", nullable = false)
    @Schema(description = "수강 정보")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    @Schema(description = "커리큐럼 단계 정보")
    private CurriculumItem curriculumItem;

    @Column(name = "IS_COMPLETED", columnDefinition = "TINYINT(1) DEFAULT 1")
    @Schema(description = "완료 여부")
    private Boolean isCompleted;

    @Column(name = "COMPLETED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "완료 시점")
    private LocalDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        if (isCompleted == null) {
            isCompleted = false;
        }
        if (Boolean.TRUE.equals(isCompleted) && completedAt == null) {
            completedAt = LocalDateTime.now();
        }
        if (Boolean.FALSE.equals(isCompleted)) {
            completedAt = null;
        }
    }
}
