package com.douzone.knitching.domain.matching.controller;

import com.douzone.knitching.domain.matching.entity.MatchingRequest;
import com.douzone.knitching.domain.matching.service.MatchingRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/matching-requests")
@RequiredArgsConstructor
@Tag(name = "MatchingRequest", description = "강사 매칭 신청 관리 API")
public class MatchingRequestController {
    private final MatchingRequestService matchingRequestService;

    @Operation(summary = "매칭 신청 생성", description = "새로운 매칭 신청을 생성합니다")
    @ApiResponse(responseCode = "200", description = "생성 성공")
    @PostMapping
    public ResponseEntity<MatchingRequest> createMatchingRequest(@RequestBody MatchingRequest matchingRequest) {
        MatchingRequest createdRequest = matchingRequestService.createMatchingRequest(matchingRequest);
        return ResponseEntity.ok(createdRequest);
    }

    @Operation(summary = "ID로 매칭 신청 조회", description = "매칭 신청 ID로 매칭 신청 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "매칭 신청 미존재")
    @GetMapping("/{matchId}")
    public ResponseEntity<MatchingRequest> getMatchingRequestById(@PathVariable Long matchId) {
        return matchingRequestService.getMatchingRequestById(matchId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "수강 ID로 매칭 신청 조회", description = "수강 신청 ID로 해당 수강의 모든 매칭 신청을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/enrollment/{enrollId}")
    public ResponseEntity<List<MatchingRequest>> getMatchingRequestsByEnrollId(@PathVariable Long enrollId) {
        List<MatchingRequest> requests = matchingRequestService.getMatchingRequestsByEnrollId(enrollId);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "강사 ID로 매칭 신청 조회", description = "강사 ID로 해당 강사의 모든 매칭 신청을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/instructor/{instId}")
    public ResponseEntity<List<MatchingRequest>> getMatchingRequestsByInstId(@PathVariable Long instId) {
        List<MatchingRequest> requests = matchingRequestService.getMatchingRequestsByInstId(instId);
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "전체 매칭 신청 조회", description = "모든 매칭 신청 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<MatchingRequest>> getAllMatchingRequests() {
        List<MatchingRequest> requests = matchingRequestService.getAllMatchingRequests();
        return ResponseEntity.ok(requests);
    }

    @Operation(summary = "매칭 신청 정보 수정", description = "매칭 신청 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{matchId}")
    public ResponseEntity<MatchingRequest> updateMatchingRequest(@PathVariable Long matchId, @RequestBody MatchingRequest matchingRequest) {
        matchingRequest.setMatchId(matchId);
        MatchingRequest updatedRequest = matchingRequestService.updateMatchingRequest(matchingRequest);
        return ResponseEntity.ok(updatedRequest);
    }

    @Operation(summary = "매칭 신청 삭제", description = "매칭 신청을 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteMatchingRequest(@PathVariable Long matchId) {
        matchingRequestService.deleteMatchingRequest(matchId);
        return ResponseEntity.noContent().build();
    }
}
