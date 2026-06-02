package com.douzone.knitching.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

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

    @Column(name = "CREATED_AT", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Schema(description = "계정 생성 일시")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
