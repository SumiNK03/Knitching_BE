package com.douzone.knitching.domain.user.controller;

import com.douzone.knitching.domain.user.dto.ToolGroupDTO;
import com.douzone.knitching.domain.user.dto.UserSkillUpdateRequestDTO;
import com.douzone.knitching.domain.user.entity.UserSkill;
import com.douzone.knitching.domain.user.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
@Tag(name = "사용자 기술", description = "사용자 기술 보유 관리 API")
public class UserSkillController {
    private final UserSkillService userSkillService;

    /**
     * 사용자 기술 목록 조회
     * 도구별(대바늘/코바늘) → 난이도별(기초/중급/고급)로 그룹화된 응답
     */
    @GetMapping
    @Operation(summary = "사용자 기술 목록 조회", description = "사용자의 기술을 도구별(대바늘/코바늘), 난이도별(기초/중급/고급)로 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기술 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청")
    })
    public ResponseEntity<List<ToolGroupDTO>> getUserSkills(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("인증되지 않은 요청입니다.");
        }
        List<ToolGroupDTO> response = userSkillService.getAllUserSkills(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 기술 목록 수정
     * 기술 숙련도와 최종 업데이트 일시 수정
     */
    @PutMapping
    @Operation(summary = "사용자 기술 목록 수정", description = "사용자의 기술 숙련도를 수정합니다 (0: 불가능, 1: 가능)")
    @RequestBody(description = "수정할 기술 목록", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "기술 수정 성공"),
            @ApiResponse(responseCode = "400", description = "요청 정보 오류 (기술 숙련도는 0 또는 1만 가능)"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청")
    })
    public ResponseEntity<List<ToolGroupDTO>> updateUserSkills(
            HttpServletRequest httpRequest,
            @Valid @org.springframework.web.bind.annotation.RequestBody UserSkillUpdateRequestDTO request) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("인증되지 않은 요청입니다.");
        }
        List<ToolGroupDTO> response = userSkillService.updateUserSkills(userId, request);
        return ResponseEntity.ok(response);
    }
}
