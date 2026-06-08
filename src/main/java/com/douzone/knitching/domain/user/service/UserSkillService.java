package com.douzone.knitching.domain.user.service;

import com.douzone.knitching.domain.user.dto.LevelGroupDTO;
import com.douzone.knitching.domain.user.dto.ToolGroupDTO;
import com.douzone.knitching.domain.user.dto.UserSkillDetailDTO;
import com.douzone.knitching.domain.user.dto.UserSkillUpdateItemDTO;
import com.douzone.knitching.domain.user.dto.UserSkillUpdateRequestDTO;
import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.user.entity.UserSkill;
import com.douzone.knitching.domain.user.repository.UserRepository;
import com.douzone.knitching.domain.user.repository.UserSkillRepository;
import com.douzone.knitching.domain.video.entity.Video;
import com.douzone.knitching.domain.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * 사용자의 모든 기술 목록 조회 (도구별, 난이도별 그룹화)
     * 기술 코드 구조: [도구]-[난이도]-[분류]-[순번]
     * 예: KNT-1-CST-01 (대바늘-기초-코잡기-01)
     */
    @Transactional(readOnly = false)
    public List<ToolGroupDTO> getAllUserSkills(Long userId) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 모든 비디오 조회
        List<Video> allVideos = videoRepository.findAll();

        // 사용자의 기존 스킬 조회
        List<UserSkill> existingSkills = userSkillRepository.findByUserUserId(userId);
        Map<String, UserSkill> skillMap = existingSkills.stream()
                .collect(Collectors.toMap(UserSkill::getTechCode, skill -> skill));

        // 모든 비디오의 기술 코드에 대해 처리
        List<UserSkill> allUserSkills = allVideos.stream()
                .map(video -> {
                    String techCode = video.getTechCode();
                    // 기존 스킬이 있으면 그것 사용, 없으면 새로 생성 (level=0)
                    return skillMap.getOrDefault(techCode, UserSkill.builder()
                            .user(user)
                            .techCode(techCode)
                            .level(0)  // 새로운 스킬은 숙련도 0으로 시작
                            .updatedAt(LocalDateTime.now())
                            .build());
                })
                .collect(Collectors.toList());

        // 새로운 스킬 저장
        allUserSkills.stream()
                .filter(skill -> skill.getSkillId() == null)  // 새로운 스킬만 필터링
                .forEach(userSkillRepository::save);

        // 도구별, 난이도별로 그룹화
        return groupSkillsByToolAndLevel(allUserSkills);
    }

    /**
     * 사용자의 기술 숙련도 수정
     * 기술 숙련도와 최종 업데이트 일시 수정
     */
    @Transactional(readOnly = false)
    public List<ToolGroupDTO> updateUserSkills(Long userId, UserSkillUpdateRequestDTO request) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 기존 스킬 맵 구성
        List<UserSkill> existingSkills = userSkillRepository.findByUserUserId(userId);
        Map<String, UserSkill> skillMap = existingSkills.stream()
                .collect(Collectors.toMap(UserSkill::getTechCode, skill -> skill));

        // 수정 요청 처리
        List<UserSkill> updatedSkills = request.getSkills().stream()
                .map(updateItem -> {
                    UserSkill skill = skillMap.get(updateItem.getTechCode());
                    if (skill == null) {
                        throw new IllegalArgumentException("존재하지 않는 기술입니다: " + updateItem.getTechCode());
                    }
                    // 숙련도 수정 (0 또는 1만 허용)
                    if (updateItem.getLevel() != 0 && updateItem.getLevel() != 1) {
                        throw new IllegalArgumentException("기술 숙련도는 0 또는 1만 가능합니다.");
                    }
                    skill.setLevel(updateItem.getLevel());
                    skill.setUpdatedAt(LocalDateTime.now());
                    return skill;
                })
                .collect(Collectors.toList());

        // 수정된 스킬 저장
        List<UserSkill> savedSkills = userSkillRepository.saveAll(updatedSkills);

        // 도구별, 난이도별로 그룹화해서 반환
        return groupSkillsByToolAndLevel(savedSkills);
    }

    /**
     * 기술 코드 파싱: [도구]-[난이도]-[분류]-[순번]
     * 예: KNT-1-CST-01 -> 도구: KNT, 난이도: 1, 분류: CST, 순번: 01
     */
    private Map<String, String> parseTeachCode(String techCode) {
        String[] parts = techCode.split("-");
        if (parts.length != 4) {
            throw new IllegalArgumentException("잘못된 기술 코드 형식입니다: " + techCode);
        }
        Map<String, String> result = new LinkedHashMap<>();
        result.put("tool", parts[0]);           // KNT, CRC
        result.put("level", parts[1]);          // 1, 2, 3
        result.put("category", parts[2]);       // CST, BAS, DEC
        result.put("sequence", parts[3]);       // 01, 02, 03...
        return result;
    }

    /**
     * 도구 코드를 도구 이름으로 변환
     */
    private String getToolName(String tool) {
        return "KNT".equals(tool) ? "대바늘" : "CRC".equals(tool) ? "코바늘" : tool;
    }

    /**
     * 난이도 번호를 난이도 이름으로 변환
     */
    private String getLevelName(Integer level) {
        return switch (level) {
            case 1 -> "기초";
            case 2 -> "중급";
            case 3 -> "고급";
            default -> "기타";
        };
    }

    /**
     * 스킬을 도구별, 난이도별로 그룹화
     */
    private List<ToolGroupDTO> groupSkillsByToolAndLevel(List<UserSkill> skills) {
        // 도구별로 먼저 그룹화
        Map<String, List<UserSkill>> toolGroups = skills.stream()
                .collect(Collectors.groupingBy(skill -> {
                    Map<String, String> parsed = parseTeachCode(skill.getTechCode());
                    return parsed.get("tool");
                }, LinkedHashMap::new, Collectors.toList()));

        // 도구별로 ToolGroupDTO 생성
        return toolGroups.entrySet().stream()
                .map(toolEntry -> {
                    String tool = toolEntry.getKey();
                    List<UserSkill> toolSkills = toolEntry.getValue();

                    // 도구 내에서 난이도별로 그룹화
                    Map<Integer, List<UserSkill>> levelGroups = toolSkills.stream()
                            .collect(Collectors.groupingBy(skill -> {
                                Map<String, String> parsed = parseTeachCode(skill.getTechCode());
                                return Integer.parseInt(parsed.get("level"));
                            }, LinkedHashMap::new, Collectors.toList()));

                    // 난이도별 그룹 생성
                    List<LevelGroupDTO> levelGroupDTOs = levelGroups.entrySet().stream()
                            .map(levelEntry -> {
                                Integer level = levelEntry.getKey();
                                List<UserSkill> levelSkills = levelEntry.getValue();

                                // 스킬 DTO 생성
                                List<UserSkillDetailDTO> skillDTOs = levelSkills.stream()
                                        .map(this::convertToDetailDTO)
                                        .collect(Collectors.toList());

                                return LevelGroupDTO.builder()
                                        .level(level)
                                        .levelName(getLevelName(level))
                                        .skills(skillDTOs)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return ToolGroupDTO.builder()
                            .tool(tool)
                            .toolName(getToolName(tool))
                            .levelGroups(levelGroupDTOs)
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * UserSkill을 UserSkillDetailDTO로 변환
     */
    private UserSkillDetailDTO convertToDetailDTO(UserSkill skill) {
        Map<String, String> parsed = parseTeachCode(skill.getTechCode());
        return UserSkillDetailDTO.builder()
                .techCode(skill.getTechCode())
                .title(skill.getVideo() != null ? skill.getVideo().getTitle() : "")
                .category(parsed.get("category"))
                .sequence(parsed.get("sequence"))
                .level(skill.getLevel())
                .updatedAt(skill.getUpdatedAt() != null ? skill.getUpdatedAt().format(DATE_FORMATTER) : "")
                .build();
    }
}
