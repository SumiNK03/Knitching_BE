package com.douzone.knitching.domain.user.controller;

import com.douzone.knitching.domain.user.entity.UserSkill;
import com.douzone.knitching.domain.user.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
@Tag(name = "UserSkill", description = "사용자 기술 보유 관리 API")
public class UserSkillController {
    private final UserSkillService userSkillService;

    @Operation(summary = "사용자 기술 등록", description = "사용자 기술을 등록합니다")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping
    public ResponseEntity<UserSkill> createUserSkill(@RequestBody UserSkill userSkill) {
        UserSkill createdSkill = userSkillService.createUserSkill(userSkill);
        return ResponseEntity.ok(createdSkill);
    }

    @Operation(summary = "ID로 사용자 기술 조회", description = "기술 ID로 사용자 기술 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "기술 미존재")
    @GetMapping("/{skillId}")
    public ResponseEntity<UserSkill> getUserSkillById(@PathVariable Long skillId) {
        return userSkillService.getUserSkillById(skillId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "사용자 ID로 기술 조회", description = "사용자 ID로 해당 사용자의 모든 기술을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserSkill>> getUserSkillsByUserId(@PathVariable Long userId) {
        List<UserSkill> skills = userSkillService.getUserSkillsByUserId(userId);
        return ResponseEntity.ok(skills);
    }

    @Operation(summary = "전체 사용자 기술 조회", description = "모든 사용자 기술 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<UserSkill>> getAllUserSkills() {
        List<UserSkill> skills = userSkillService.getAllUserSkills();
        return ResponseEntity.ok(skills);
    }

    @Operation(summary = "사용자 기술 정보 수정", description = "사용자 기술 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{skillId}")
    public ResponseEntity<UserSkill> updateUserSkill(@PathVariable Long skillId, @RequestBody UserSkill userSkill) {
        userSkill.setSkillId(skillId);
        UserSkill updatedSkill = userSkillService.updateUserSkill(userSkill);
        return ResponseEntity.ok(updatedSkill);
    }

    @Operation(summary = "사용자 기술 삭제", description = "사용자 기술을 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteUserSkill(@PathVariable Long skillId) {
        userSkillService.deleteUserSkill(skillId);
        return ResponseEntity.noContent().build();
    }
}
