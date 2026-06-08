package com.douzone.knitching.domain.enrollment.controller;

import com.douzone.knitching.domain.enrollment.dto.EnrollmentListResponseDTO;
import com.douzone.knitching.domain.enrollment.dto.EnrollmentResponseDTO;
import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import com.douzone.knitching.domain.enrollment.service.EnrollmentService;
import com.douzone.knitching.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollment", description = "수강 신청 관리 API")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "수강 신청 생성", description = "도안 ID로 수강 신청을 생성합니다")
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @ApiResponse(responseCode = "404", description = "사용자 또는 커리큘럼 미존재")
    @ApiResponse(responseCode = "409", description = "이미 신청한 도안")
    @PostMapping("/{patternId}")
    public ResponseEntity<EnrollmentResponseDTO> createEnrollment(
            @PathVariable Long patternId,
            @RequestHeader("Authorization") String authorization
    ) {
        Long userId = jwtTokenProvider.getUserIdFromAuthHeader(authorization);
        EnrollmentResponseDTO response = enrollmentService.enrollPattern(userId, patternId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "내 수강 신청 조회", description = "헤더의 사용자 정보로 해당 사용자의 수강 신청 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<EnrollmentListResponseDTO>> getMyEnrollments(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtTokenProvider.getUserIdFromAuthHeader(authorization);
        List<EnrollmentListResponseDTO> enrollments = enrollmentService.getMyEnrollments(userId);
        return ResponseEntity.ok(enrollments);
    }

    @Operation(summary = "ID로 수강 신청 조회", description = "수강 신청 ID로 수강 신청 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "수강 신청 미존재")
    @GetMapping("/{enrollId}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long enrollId) {
        return enrollmentService.getEnrollmentById(enrollId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "사용자 ID로 수강 신청 조회", description = "사용자 ID로 해당 사용자의 모든 수강 신청을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByUserId(@PathVariable Long userId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        return ResponseEntity.ok(enrollments);
    }

    @Operation(summary = "커리큘럼 ID로 수강 신청 조회", description = "커리큘럼 ID로 해당 커리큘럼의 모든 수강 신청을 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/curriculum/{curriId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByCurriId(@PathVariable Long curriId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCurriId(curriId);
        return ResponseEntity.ok(enrollments);
    }

    @Operation(summary = "전체 수강 신청 조회", description = "모든 수강 신청 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/all")
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @Operation(summary = "수강 신청 정보 수정", description = "수강 신청 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{enrollId}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long enrollId, @RequestBody Enrollment enrollment) {
        enrollment.setEnrollId(enrollId);
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(enrollment);
        return ResponseEntity.ok(updatedEnrollment);
    }

    @Operation(summary = "수강 신청 삭제", description = "수강 신청을 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{enrollId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long enrollId) {
        enrollmentService.deleteEnrollment(enrollId);
        return ResponseEntity.noContent().build();
    }
}
