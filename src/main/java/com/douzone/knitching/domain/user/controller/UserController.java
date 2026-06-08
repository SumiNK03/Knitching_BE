package com.douzone.knitching.domain.user.controller;

import com.douzone.knitching.domain.user.dto.LoginRequestDTO;
import com.douzone.knitching.domain.user.dto.LoginResponseDTO;
import com.douzone.knitching.domain.user.dto.SignUpRequestDTO;
import com.douzone.knitching.domain.user.dto.UpdateUserRequestDTO;
import com.douzone.knitching.domain.user.dto.UserResponseDTO;
import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.user.service.UserService;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자 관리", description = "사용자 회원가입, 로그인, 정보 수정 등의 API")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다")
    @RequestBody(description = "회원가입 정보", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "요청 정보 오류")
    })
    public ResponseEntity<UserResponseDTO> signUp(@Valid @org.springframework.web.bind.annotation.RequestBody SignUpRequestDTO request) {
        UserResponseDTO response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 정보를 이용하여 로그인합니다")
    @RequestBody(description = "로그인 정보 (ID, 비밀번호)", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공, JWT 토큰 반환"),
            @ApiResponse(responseCode = "400", description = "아이디 또는 비밀번호 오류")
    })
    public ResponseEntity<LoginResponseDTO> login(@Valid @org.springframework.web.bind.annotation.RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 정보 수정
     */
    @PutMapping
    @Operation(summary = "사용자 정보 수정", description = "사용자의 정보를 수정합니다 (이름, 닉네임, 성별, 주소, 이메일)")
    @RequestBody(description = "수정할 사용자 정보", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 수정 성공"),
            @ApiResponse(responseCode = "400", description = "요청 정보 오류"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청")
    })
    public ResponseEntity<UserResponseDTO> updateUser(
            HttpServletRequest httpRequest,
            @Valid @org.springframework.web.bind.annotation.RequestBody UpdateUserRequestDTO request) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("인증되지 않은 요청입니다.");
        }
        UserResponseDTO response = userService.updateUser(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 사용자 정보 조회
     */
    @GetMapping
    @Operation(summary = "사용자 정보 조회", description = "로그인한 사용자의 정보를 조회합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 요청")
    })
    public ResponseEntity<UserResponseDTO> getUser(HttpServletRequest httpRequest) {
        Long userId = (Long) httpRequest.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("인증되지 않은 요청입니다.");
        }
        UserResponseDTO response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }
}
