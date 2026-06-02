package com.douzone.knitching.domain.instructor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Table(name = "instructors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "강사 정보")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INST_ID")
    @Schema(description = "강사 고유 번호")
    private Long instId;

    @Column(name = "LOGIN_ID", nullable = false, unique = true, length = 50)
    @Schema(description = "로그인 ID")
    private String loginId;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    @Schema(description = "비밀번호")
    private String password;

    @Column(name = "NAME", nullable = false, length = 50)
    @Schema(description = "강사 이름")
    private String name;

    @Column(name = "EMAIL", unique = true, length = 100)
    @Schema(description = "이메일")
    private String email;

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

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "계정 생성 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (maxStudents == null) maxStudents = 5;
        if (currentStudent == null) currentStudent = 0;
        if (isOnline == null) isOnline = true;
        if (isOffline == null) isOffline = true;
        createdAt = LocalDateTime.now();
    }
}
