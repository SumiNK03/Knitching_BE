package com.douzone.knitching.domain.user.controller;

import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "사용자 관리 API")
public class UserController {
    private final UserService userService;

    @Operation(summary = "사용자 생성", description = "새로운 사용자를 생성합니다")
    @ApiResponse(responseCode = "200", description = "사용자 생성 성공")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @Operation(summary = "ID로 사용자 조회", description = "사용자 ID로 사용자 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "사용자 미존재")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "로그인 ID로 사용자 조회", description = "로그인 ID로 사용자 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "사용자 미존재")
    @GetMapping("/login/{loginId}")
    public ResponseEntity<User> getUserByLoginId(@PathVariable String loginId) {
        return userService.getUserByLoginId(loginId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "이메일로 사용자 조회", description = "이메일로 사용자 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "사용자 미존재")
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "전체 사용자 조회", description = "모든 사용자 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setUserId(userId);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
