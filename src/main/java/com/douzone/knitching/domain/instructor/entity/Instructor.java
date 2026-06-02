package com.douzone.knitching.domain.instructor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import com.douzone.knitching.domain.user.entity.User;

@Entity
@Table(name = "instructors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "강사 프로필 정보")
public class Instructor {
    /**
     * 강사 ID = User ID (식별 관계)
     * User와의 1:1 식별 관계로, User의 PK를 그대로 사용
     */
    @Id
    @Column(name = "INST_ID")
    @Schema(description = "강사 고유 번호 (사용자 ID와 동일)")
    private Long instId;

    /**
     * User와의 1:1 식별 관계 매핑
     * @MapsId: instId가 User의 userId를 매핑함을 명시
     */
    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INST_ID", referencedColumnName = "USER_ID")
    @Schema(description = "연관된 사용자 정보")
    private User user;

    @Column(name = "MAX_STUDENTS", columnDefinition = "INT DEFAULT 5")
    @Schema(description = "최대 수강생 수")
    private Integer maxStudents;

    @Column(name = "CURRENT_STUDENT", columnDefinition = "INT DEFAULT 0")
    @Schema(description = "현재 관리중인 수강생 수")
    private Integer currentStudent;

    @Column(name = "IS_ONLINE", columnDefinition = "TINYINT(1) DEFAULT 1")
    @Schema(description = "화상 코칭 지원 여부")
    private Boolean isOnline;

    @Column(name = "IS_OFFLINE", columnDefinition = "TINYINT(1) DEFAULT 1")
    @Schema(description = "대면 코칭 지원 여부")
    private Boolean isOffline;

    @Column(name = "LOCATION", length = 255)
    @Schema(description = "활동 가능 지역")
    private String location;

    @PrePersist
    protected void onCreate() {
        if (maxStudents == null) maxStudents = 5;
        if (currentStudent == null) currentStudent = 0;
        if (isOnline == null) isOnline = true;
        if (isOffline == null) isOffline = true;
    }
}
