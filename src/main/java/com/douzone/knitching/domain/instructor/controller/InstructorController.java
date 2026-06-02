package com.douzone.knitching.domain.instructor.controller;

import com.douzone.knitching.domain.instructor.entity.Instructor;
import com.douzone.knitching.domain.instructor.service.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/instructors")
@RequiredArgsConstructor
@Tag(name = "Instructor", description = "강사 관리 API")
public class InstructorController {
    private final InstructorService instructorService;

    @Operation(summary = "강사 생성", description = "새로운 강사를 생성합니다")
    @ApiResponse(responseCode = "200", description = "강사 생성 성공")
    @PostMapping
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
        Instructor createdInstructor = instructorService.createInstructor(instructor);
        return ResponseEntity.ok(createdInstructor);
    }

    @Operation(summary = "ID로 강사 조회", description = "강사 ID로 강사 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "강사 미존재")
    @GetMapping("/{instId}")
    public ResponseEntity<Instructor> getInstructorById(@PathVariable Long instId) {
        return instructorService.getInstructorById(instId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "로그인 ID로 강사 조회", description = "로그인 ID로 강사 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "강사 미존재")
    @GetMapping("/login/{loginId}")
    public ResponseEntity<Instructor> getInstructorByLoginId(@PathVariable String loginId) {
        return instructorService.getInstructorByLoginId(loginId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "이메일로 강사 조회", description = "이메일로 강사 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "강사 미존재")
    @GetMapping("/email/{email}")
    public ResponseEntity<Instructor> getInstructorByEmail(@PathVariable String email) {
        return instructorService.getInstructorByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "전체 강사 조회", description = "모든 강사 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        List<Instructor> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @Operation(summary = "강사 정보 수정", description = "강사 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{instId}")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable Long instId, @RequestBody Instructor instructor) {
        instructor.setInstId(instId);
        Instructor updatedInstructor = instructorService.updateInstructor(instructor);
        return ResponseEntity.ok(updatedInstructor);
    }

    @Operation(summary = "강사 삭제", description = "강사를 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{instId}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long instId) {
        instructorService.deleteInstructor(instId);
        return ResponseEntity.noContent().build();
    }
}
