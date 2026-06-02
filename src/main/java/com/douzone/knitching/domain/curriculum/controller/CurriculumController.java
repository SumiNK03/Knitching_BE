package com.douzone.knitching.domain.curriculum.controller;

import com.douzone.knitching.domain.curriculum.entity.Curriculum;
import com.douzone.knitching.domain.curriculum.service.CurriculumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/curriculums")
@RequiredArgsConstructor
@Tag(name = "Curriculum", description = "커리큘럼 관리 API")
public class CurriculumController {
    private final CurriculumService curriculumService;

    @Operation(summary = "커리큘럼 등록", description = "새로운 커리큘럼을 등록합니다")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping
    public ResponseEntity<Curriculum> createCurriculum(@RequestBody Curriculum curriculum) {
        Curriculum createdCurriculum = curriculumService.createCurriculum(curriculum);
        return ResponseEntity.ok(createdCurriculum);
    }

    @Operation(summary = "ID로 커리큘럼 조회", description = "커리큘럼 ID로 커리큘럼 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "커리큘럼 미존재")
    @GetMapping("/{curriId}")
    public ResponseEntity<Curriculum> getCurriculumById(@PathVariable Long curriId) {
        return curriculumService.getCurriculumById(curriId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "전체 커리큘럼 조회", description = "모든 커리큘럼 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<Curriculum>> getAllCurriculums() {
        List<Curriculum> curriculums = curriculumService.getAllCurriculums();
        return ResponseEntity.ok(curriculums);
    }

    @Operation(summary = "커리큘럼 정보 수정", description = "커리큘럼 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{curriId}")
    public ResponseEntity<Curriculum> updateCurriculum(@PathVariable Long curriId, @RequestBody Curriculum curriculum) {
        curriculum.setCurriId(curriId);
        Curriculum updatedCurriculum = curriculumService.updateCurriculum(curriculum);
        return ResponseEntity.ok(updatedCurriculum);
    }

    @Operation(summary = "커리큘럼 삭제", description = "커리큘럼을 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{curriId}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long curriId) {
        curriculumService.deleteCurriculum(curriId);
        return ResponseEntity.noContent().build();
    }
}
