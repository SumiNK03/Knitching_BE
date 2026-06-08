package com.douzone.knitching.domain.user.entity;

import java.time.LocalDateTime;

import com.douzone.knitching.domain.instructor.entity.Instructor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 정보")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    @Schema(description = "사용자 고유 번호")
    private Long userId;

    @Column(name = "LOGIN_ID", nullable = false, unique = true, length = 50)
    @Schema(description = "로그인 ID")
    private String loginId;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    @Schema(description = "비밀번호")
    private String password;

    @Column(name = "NAME", nullable = false, length = 50)
    @Schema(description = "사용자 이름")
    private String name;

    @Column(name = "EMAIL", unique = true, length = 100)
    @Schema(description = "이메일")
    private String email;

    @Column(name = "ROLE", nullable = false, columnDefinition = "ENUM('USER', 'INSTRUCTOR') DEFAULT 'USER'")
    @Enumerated(EnumType.STRING)
    @Schema(description = "사용자 역할")
    private RoleType role;

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "계정 생성 일시")
    private LocalDateTime createdAt;

    @Column(name = "NICKNAME", length = 50)
    @Schema(description = "닉네임")
    private String nickname;

    @Column(name = "GENDER", columnDefinition = "ENUM('male', 'female', 'other')")
    @Enumerated(EnumType.STRING)
    @Schema(description = "성별")
    private GenderType gender;

    @Column(name = "ADDRESS", length = 255)
    @Schema(description = "주소")
    private String address;

    // 양방향 관계: Instructor와의 1:1 관계 (선택사항, 강사인 경우만 존재)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "강사 정보 (역할이 INSTRUCTOR일 때만 존재)")
    private Instructor instructor;

    @PrePersist
    protected void onCreate() {
        if (role == null) role = RoleType.USER;
        createdAt = LocalDateTime.now();
    }
}
