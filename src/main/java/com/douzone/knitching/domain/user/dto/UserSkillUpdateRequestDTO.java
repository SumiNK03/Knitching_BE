package com.douzone.knitching.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "사용자 기술 수정 요청 DTO")
public class UserSkillUpdateRequestDTO {
    
    @NotEmpty(message = "수정할 기술 목록은 비어있을 수 없습니다")
    @Valid
    @Schema(description = "수정할 기술 목록")
    private List<UserSkillUpdateItemDTO> skills;
}
