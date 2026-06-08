package com.douzone.knitching.domain.user.dto;

import com.douzone.knitching.domain.user.entity.GenderType;
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
@Schema(description = "사용자 정보 수정 요청 DTO")
public class UpdateUserRequestDTO {
    
    @NotBlank(message = "이름은 필수입니다")
    @Size(min = 2, max = 50, message = "이름은 2자 이상 50자 이하여야 합니다")
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    
    @Size(max = 50, message = "닉네임은 50자 이하여야 합니다")
    @Schema(description = "닉네임", example = "닉네임123")
    private String nickname;
    
    @Schema(description = "성별", example = "male")
    private GenderType gender;
    
    @Size(max = 255, message = "주소는 255자 이하여야 합니다")
    @Schema(description = "주소", example = "서울시 강남구 테헤란로 123")
    private String address;
    
    @Email(message = "유효한 이메일 주소를 입력하세요")
    @Schema(description = "이메일", example = "user@example.com")
    private String email;
}
