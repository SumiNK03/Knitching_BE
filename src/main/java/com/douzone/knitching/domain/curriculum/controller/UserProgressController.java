package com.douzone.knitching.domain.curriculum.controller;

import com.douzone.knitching.domain.curriculum.dto.CompleteUserProgressResponseDTO;
import com.douzone.knitching.domain.curriculum.entity.UserProgress;
import com.douzone.knitching.domain.curriculum.service.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user-progress")
@RequiredArgsConstructor
@Tag(name = "UserProgress", description = "사용자 학습 진도 관리 API")
public class UserProgressController {
    private final UserProgressService userProgressService;

    @Operation(summary = "학습 진도 생성", description = "새로운 학습 진도를 생성합니다")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    @PostMapping
    public ResponseEntity<UserProgress> createUserProgress(@RequestBody UserProgress userProgress) {
        UserProgress createdProgress = userProgressService.createUserProgress(userProgress);
        return ResponseEntity.ok(createdProgress);
    }

    @Operation(summary = "ID로 학습 진도 조회", description = "진도 ID로 학습 진도 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "진도 미존재")
    @GetMapping("/{progressId}")
    public ResponseEntity<UserProgress> getUserProgressById(@PathVariable Long progressId) {
        return userProgressService.getUserProgressById(progressId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "수강 ID로 학습 진도 조회", description = "수강 신청 ID로 해당 수강의 모든 학습 진도를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/enrollment/{enrollId}")
    public ResponseEntity<List<UserProgress>> getUserProgressByEnrollId(@PathVariable Long enrollId) {
        List<UserProgress> progress = userProgressService.getUserProgressByEnrollId(enrollId);
        return ResponseEntity.ok(progress);
    }

    @Operation(summary = "전체 학습 진도 조회", description = "모든 학습 진도 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<UserProgress>> getAllUserProgress() {
        List<UserProgress> progress = userProgressService.getAllUserProgress();
        return ResponseEntity.ok(progress);
    }

    @Operation(summary = "학습 진도 정보 수정", description = "학습 진도 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{progressId}")
    public ResponseEntity<UserProgress> updateUserProgress(@PathVariable Long progressId, @RequestBody UserProgress userProgress) {
        userProgress.setProgressId(progressId);
        UserProgress updatedProgress = userProgressService.updateUserProgress(userProgress);
        return ResponseEntity.ok(updatedProgress);
    }

    @Operation(summary = "학습 진도 완료 처리", description = "진도 ID로 해당 학습 진도를 완료 처리합니다")
    @ApiResponse(responseCode = "200", description = "완료 처리 성공")
    @ApiResponse(responseCode = "404", description = "진도 미존재")
    @PatchMapping("/{progressId}/complete")
    public ResponseEntity<CompleteUserProgressResponseDTO> completeUserProgress(@PathVariable Long progressId) {
        CompleteUserProgressResponseDTO response = userProgressService.completeUserProgress(progressId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "학습 진도 삭제", description = "학습 진도를 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{progressId}")
    public ResponseEntity<Void> deleteUserProgress(@PathVariable Long progressId) {
        userProgressService.deleteUserProgress(progressId);
        return ResponseEntity.noContent().build();
    }
}
