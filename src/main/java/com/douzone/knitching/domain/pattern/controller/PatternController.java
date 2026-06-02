package com.douzone.knitching.domain.pattern.controller;

import com.douzone.knitching.domain.pattern.entity.Pattern;
import com.douzone.knitching.domain.pattern.service.PatternService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/patterns")
@RequiredArgsConstructor
@Tag(name = "Pattern", description = "도안 관리 API")
public class PatternController {
    private final PatternService patternService;

    @Operation(summary = "도안 등록", description = "새로운 도안을 등록합니다")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping
    public ResponseEntity<Pattern> createPattern(@RequestBody Pattern pattern) {
        Pattern createdPattern = patternService.createPattern(pattern);
        return ResponseEntity.ok(createdPattern);
    }

    @Operation(summary = "ID로 도안 조회", description = "도안 ID로 도안 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "도안 미존재")
    @GetMapping("/{patternId}")
    public ResponseEntity<Pattern> getPatternById(@PathVariable Long patternId) {
        return patternService.getPatternById(patternId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "전체 도안 조회", description = "모든 도안 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<Pattern>> getAllPatterns() {
        List<Pattern> patterns = patternService.getAllPatterns();
        return ResponseEntity.ok(patterns);
    }

    @Operation(summary = "도안 정보 수정", description = "도안 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{patternId}")
    public ResponseEntity<Pattern> updatePattern(@PathVariable Long patternId, @RequestBody Pattern pattern) {
        pattern.setPatternId(patternId);
        Pattern updatedPattern = patternService.updatePattern(pattern);
        return ResponseEntity.ok(updatedPattern);
    }

    @Operation(summary = "도안 삭제", description = "도안을 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{patternId}")
    public ResponseEntity<Void> deletePattern(@PathVariable Long patternId) {
        patternService.deletePattern(patternId);
        return ResponseEntity.noContent().build();
    }
}
