package com.douzone.knitching.domain.user.dto;

import com.douzone.knitching.domain.user.entity.GenderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "사용자 정보 응답 DTO")
public class UserResponseDTO {
    
    @Schema(description = "사용자 고유 번호", example = "1")
    private Long userId;
    
    @Schema(description = "로그인 ID", example = "user123")
    private String loginId;
    
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    
    @Schema(description = "이메일", example = "user@example.com")
    private String email;
    
    @Schema(description = "사용자 역할", example = "USER")
    private String role;
    
    @Schema(description = "닉네임", example = "닉네임123")
    private String nickname;
    
    @Schema(description = "성별", example = "male")
    private GenderType gender;
    
    @Schema(description = "주소", example = "서울시 강남구 테헤란로 123")
    private String address;
}
