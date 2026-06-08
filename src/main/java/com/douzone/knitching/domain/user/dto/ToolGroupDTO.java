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
@Schema(description = "도구별 기술 그룹 DTO")
public class ToolGroupDTO {
    
    @Schema(description = "도구 코드", example = "KNT")
    private String tool;
    
    @Schema(description = "도구 이름", example = "대바늘")
    private String toolName;
    
    @Schema(description = "난이도별 기술 그룹 목록")
    private List<LevelGroupDTO> levelGroups;
}
