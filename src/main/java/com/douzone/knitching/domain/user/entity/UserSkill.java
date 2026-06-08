package com.douzone.knitching.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.video.entity.Video;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 기술 보유 정보")
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SKILL_ID")
    @Schema(description = "기술 보유 기록 고유 번호")
    private Long skillId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @Schema(description = "사용자 정보")
    private User user;

    @Column(name = "TECH_CODE", nullable = false, length = 20)
    @Schema(description = "기술 식별 코드")
    private String techCode;

    @ManyToOne
    @JoinColumn(name = "TECH_CODE", referencedColumnName = "TECH_CODE", insertable = false, updatable = false)
    @Schema(description = "기술 영상 정보")
    private Video video;

    @Column(name = "LEVEL", columnDefinition = "INT DEFAULT 1")
    @Schema(description = "기술 숙련도")
    private Integer level;

    @Column(name = "UPDATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @Schema(description = "최종 업데이트 일시")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (level == null) level = 1;
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
