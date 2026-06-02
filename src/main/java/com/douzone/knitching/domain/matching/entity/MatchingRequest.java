package com.douzone.knitching.domain.matching.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import com.douzone.knitching.domain.instructor.entity.Instructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "matching_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "강사 매챭 신청 정보")
public class MatchingRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MATCH_ID")
    @Schema(description = "매챭 고유 번호")
    private Long matchId;

    @ManyToOne
    @JoinColumn(name = "ENROLL_ID", nullable = false)
    @Schema(description = "수강 정보")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "INST_ID", nullable = false)
    @Schema(description = "강사 정보")
    private Instructor instructor;

    @Column(name = "COACH_TYPE", nullable = false, columnDefinition = "ENUM('ONLINE','OFFLINE')")
    @Enumerated(EnumType.STRING)
    @Schema(description = "코칭 검색 유형")
    private CoachType coachType;

    @Column(name = "MATCH_STATUS", columnDefinition = "ENUM('WAIT','ACCEPT','DENY') DEFAULT 'WAIT'")
    @Enumerated(EnumType.STRING)
    @Schema(description = "매챭 실차 상태")
    private MatchStatus matchStatus;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "매챭 신청 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (matchStatus == null) matchStatus = MatchStatus.WAIT;
        createdAt = LocalDateTime.now();
    }

    public enum CoachType {
        ONLINE, OFFLINE
    }

    public enum MatchStatus {
        WAIT, ACCEPT, DENY
    }
}
