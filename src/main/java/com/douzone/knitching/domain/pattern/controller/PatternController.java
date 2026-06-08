package com.douzone.knitching.domain.pattern.controller;

import com.douzone.knitching.domain.pattern.dto.PatternDetailResponse;
import com.douzone.knitching.domain.pattern.dto.PatternListResponse;
import com.douzone.knitching.domain.pattern.service.PatternService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patterns")
@RequiredArgsConstructor
@Tag(name = "도안 관리", description = "도안 조회 API")
public class PatternController {

    private final PatternService patternService;

    @GetMapping("/{patternId}")
    @Operation(
            summary = "도안 상세 조회",
            description = "패턴 ID를 이용하여 도안 상세 정보를 조회합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = PatternDetailResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않는 도안"
            )
    })
    public ResponseEntity<PatternDetailResponse> getPatternById(

            @Parameter(
                    description = "조회할 도안 ID",
                    example = "1"
            )
            @PathVariable Long patternId
    ) {
        return ResponseEntity.ok(patternService.getPatternById(patternId));
    }

    @GetMapping
    @Operation(
            summary = "도안 목록 조회",
            description = """
                    도안 목록을 조회합니다.

                    정렬 방식
                    - latest : 최신순
                    - popular : 인기순 (좋아요 × 10 + 조회수)

                    도구 필터
                    - all : 전체
                    - 대바늘
                    - 코바늘

                    페이지네이션 지원
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            array = @ArraySchema(
                                    schema = @Schema(implementation = PatternListResponse.class)
                            )
                    )
            )
    })
    public ResponseEntity<Page<PatternListResponse>> getAllPatterns(

            @Parameter(
                    description = "정렬 방식 (latest, popular)",
                    example = "popular"
            )
            @RequestParam(defaultValue = "latest")
            String sort,

            @Parameter(
                    description = "도구 필터 (all, 대바늘, 코바늘)",
                    example = "코바늘"
            )
            @RequestParam(defaultValue = "all")
            String tool,

            @Parameter(
                    description = "페이지 번호 (0부터 시작)",
                    example = "0"
            )
            @RequestParam(defaultValue = "0")
            int page,

            @Parameter(
                    description = "페이지당 데이터 수",
                    example = "10"
            )
            @RequestParam(defaultValue = "10")
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                patternService.getPatterns(sort, tool, pageable)
        );
    }
}