package com.douzone.knitching.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "난이도별 기술 그룹 DTO")
public class LevelGroupDTO {
    
    @Schema(description = "난이도 (1: 기초, 2: 중급, 3: 고급)", example = "1")
    private Integer level;
    
    @Schema(description = "난이도 이름", example = "기초")
    private String levelName;
    
    @Schema(description = "해당 난이도의 기술 목록")
    private List<UserSkillDetailDTO> skills;
}
