package com.douzone.knitching.domain.enrollment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.curriculum.entity.Curriculum;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "수강 신청 정보")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENROLL_ID")
    @Schema(description = "수강 고유 번호")
    private Long enrollId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @Schema(description = "사용자 정보")
    private User user;

    @ManyToOne
    @JoinColumn(name = "CURRI_ID", nullable = false)
    @Schema(description = "커리큐럼 정보")
    private Curriculum curriculum;

    @Column(name = "STATUS", nullable = false, columnDefinition = "ENUM('PRE','COACH','DONE') DEFAULT 'PRE'")
    @Enumerated(EnumType.STRING)
    @Schema(description = "수강 상태")
    private EnrollmentStatus status;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "수강 생성 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (status == null) status = EnrollmentStatus.PRE;
        createdAt = LocalDateTime.now();
    }

    public enum EnrollmentStatus {
        PRE, COACH, DONE
    }
}
