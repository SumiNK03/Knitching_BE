package com.douzone.knitching.domain.curriculum.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.pattern.entity.Pattern;
import com.douzone.knitching.domain.instructor.entity.Instructor;

@Entity
@Table(name = "curriculum")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "커리큐럼 정보")
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CURRI_ID")
    @Schema(description = "커리큐럼 고유 번호")
    private Long curriId;

    @ManyToOne
    @JoinColumn(name = "PATTERN_ID", nullable = false)
    @Schema(description = "도안 정보")
    private Pattern pattern;

    @ManyToOne
    @JoinColumn(name = "INST_ID", nullable = false)
    @Schema(description = "강사 정보")
    private Instructor instructor;

    @Column(name = "IS_CUSTOM", columnDefinition = "TINYINT(1) DEFAULT 0")
    @Schema(description = "개인용 도안 여부")
    private Boolean isCustom;

    @PrePersist
    protected void onCreate() {
        if (isCustom == null) isCustom = false;
    }
}
