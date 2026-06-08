package com.douzone.knitching.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 요청 DTO")
public class SignUpRequestDTO {
    
    @NotBlank(message = "로그인 ID는 필수입니다")
    @Size(min = 3, max = 50, message = "로그인 ID는 3자 이상 50자 이하여야 합니다")
    @Schema(description = "로그인 ID", example = "user123")
    private String loginId;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 6, max = 255, message = "비밀번호는 6자 이상 255자 이하여야 합니다")
    @Schema(description = "비밀번호", example = "password123!")
    private String password;
    
    @NotBlank(message = "비밀번호 확인은 필수입니다")
    @Schema(description = "비밀번호 확인", example = "password123!")
    private String passwordConfirm;
    
    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다")
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    
    @Email(message = "유효한 이메일 주소를 입력하세요")
    @Schema(description = "이메일", example = "user@example.com")
    private String email;
    
    @Size(max = 50, message = "닉네임은 50자 이하여야 합니다")
    @Schema(description = "닉네임", example = "닉네임123")
    private String nickname;
}
