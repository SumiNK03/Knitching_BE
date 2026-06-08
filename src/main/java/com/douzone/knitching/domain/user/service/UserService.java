package com.douzone.knitching.domain.user.service;

import com.douzone.knitching.domain.user.dto.LoginRequestDTO;
import com.douzone.knitching.domain.user.dto.LoginResponseDTO;
import com.douzone.knitching.domain.user.dto.SignUpRequestDTO;
import com.douzone.knitching.domain.user.dto.UpdateUserRequestDTO;
import com.douzone.knitching.domain.user.dto.UserResponseDTO;
import com.douzone.knitching.domain.user.entity.RoleType;
import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.user.repository.UserRepository;
import com.douzone.knitching.global.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    /**
     * 회원가입
     */
    public UserResponseDTO signUp(SignUpRequestDTO request) {
        // 로그인 ID 중복 확인
        if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 로그인 ID입니다.");
        }

        // 이메일 중복 확인
        if (request.getEmail() != null && userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호와 비밀번호 확인 일치 여부
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .role(RoleType.USER)
                // address는 비워두고 회원가입
                .build();

        User savedUser = userRepository.save(user);
        return toUserResponseDTO(savedUser);
    }

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByLoginId(request.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.createToken(user.getUserId());

        return LoginResponseDTO.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .role(user.getRole().name())
                .token(token)
                .build();
    }

    /**
     * 사용자 정보 수정
     */
    public UserResponseDTO updateUser(Long userId, UpdateUserRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        user.setName(request.getName());
        user.setNickname(request.getNickname());
        user.setGender(request.getGender());
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());

        User updatedUser = userRepository.save(user);
        return toUserResponseDTO(updatedUser);
    }

    /**
     * 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return toUserResponseDTO(user);
    }

    /**
     * User 엔티티를 UserResponseDTO로 변환
     */
    private UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .address(user.getAddress())
                .build();
    }
}
