package com.douzone.knitching.domain.enrollment.service;

import com.douzone.knitching.domain.curriculum.entity.Curriculum;
import com.douzone.knitching.domain.curriculum.entity.CurriculumItem;
import com.douzone.knitching.domain.curriculum.entity.UserProgress;
import com.douzone.knitching.domain.curriculum.repository.CurriculumItemRepository;
import com.douzone.knitching.domain.curriculum.repository.CurriculumRepository;
import com.douzone.knitching.domain.curriculum.repository.UserProgressRepository;
import com.douzone.knitching.domain.enrollment.dto.EnrollmentItemResponseDTO;
import com.douzone.knitching.domain.enrollment.dto.EnrollmentListResponseDTO;
import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import com.douzone.knitching.domain.enrollment.repository.EnrollmentRepository;
import com.douzone.knitching.domain.enrollment.dto.EnrollmentResponseDTO;
import com.douzone.knitching.domain.pattern.entity.Pattern;
import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.user.entity.UserSkill;
import com.douzone.knitching.domain.user.repository.UserRepository;
import com.douzone.knitching.domain.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CurriculumRepository curriculumRepository;
    private final CurriculumItemRepository curriculumItemRepository;
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final UserProgressRepository userProgressRepository;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(Objects.requireNonNull(enrollment));
    }

    public EnrollmentResponseDTO enrollPattern(Long userId, Long patternId) {
        Long normalizedUserId = Objects.requireNonNull(userId);
        Long normalizedPatternId = Objects.requireNonNull(patternId);

        User user = userRepository.findById(normalizedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));

        Curriculum curriculum = curriculumRepository.findFirstByPatternPatternIdOrderByCurriIdAsc(normalizedPatternId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "도안에 연결된 커리큘럼을 찾을 수 없습니다."));

        if (enrollmentRepository.existsByUserUserIdAndCurriculumPatternPatternId(normalizedUserId, normalizedPatternId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 신청한 도안입니다.");
        }

        Enrollment enrollment = new Enrollment(null, user, curriculum, Enrollment.EnrollmentStatus.PRE, null);

        Enrollment savedEnrollment = enrollmentRepository.save(Objects.requireNonNull(enrollment));

        List<CurriculumItem> curriculumItems = curriculumItemRepository.findByCurriculumCurriId(curriculum.getCurriId());
        Map<String, Integer> userSkillLevelMap = userSkillRepository.findByUserUserId(normalizedUserId).stream()
            .collect(Collectors.toMap(
                    UserSkill::getTechCode,
                    skill -> {
                        Integer level = skill.getLevel();
                        return level == null ? 0 : level;
                    },
                    (left, right) -> left
            ));

        List<UserProgress> progressList = curriculumItems.stream()
            .map(item -> {
                boolean completed = isAlreadyAvailable(item, userSkillLevelMap);
                UserProgress progress = new UserProgress();
                progress.setEnrollment(savedEnrollment);
                progress.setCurriculumItem(item);
                progress.setIsCompleted(completed);
                progress.setCompletedAt(completed ? LocalDateTime.now() : null);
                return progress;
            })
            .collect(Collectors.toList());

        userProgressRepository.saveAll(Objects.requireNonNull(progressList));

        Pattern pattern = curriculum.getPattern();
        return new EnrollmentResponseDTO(
            savedEnrollment.getEnrollId(),
            pattern != null ? pattern.getPatternId() : normalizedPatternId,
            pattern != null ? pattern.getTitle() : "",
            savedEnrollment.getStatus() != null ? savedEnrollment.getStatus().name() : Enrollment.EnrollmentStatus.PRE.name(),
            savedEnrollment.getCreatedAt() != null ? savedEnrollment.getCreatedAt().format(DATE_TIME_FORMATTER) : null
        );
    }

    private boolean isAlreadyAvailable(CurriculumItem item, Map<String, Integer> userSkillLevelMap) {
        if (item.getTechCode() == null) {
            return false;
        }
        return userSkillLevelMap.getOrDefault(item.getTechCode(), 0) > 0;
    }

    @Transactional(readOnly = true)
    public Optional<Enrollment> getEnrollmentById(Long enrollId) {
        return enrollmentRepository.findById(Objects.requireNonNull(enrollId));
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.findByUserUserId(Objects.requireNonNull(userId));
    }

    @Transactional(readOnly = true)
    public List<EnrollmentListResponseDTO> getMyEnrollments(Long userId) {
        Long normalizedUserId = Objects.requireNonNull(userId);
        List<Enrollment> enrollments = enrollmentRepository.findByUserUserIdOrderByCreatedAtDesc(normalizedUserId);

        return enrollments.stream()
                .map(enrollment -> {
                    Curriculum curriculum = enrollment.getCurriculum();
                    Pattern pattern = curriculum.getPattern();
                    User author = null;
                    if (pattern != null && pattern.getInstId() != null) {
                        Long authorId = Objects.requireNonNull(pattern.getInstId());
                        author = userRepository.findById(authorId).orElse(null);
                    }

                    List<CurriculumItem> curriculumItems = curriculumItemRepository.findByCurriculumCurriIdOrderByStepOrderAsc(curriculum.getCurriId());
                    List<UserProgress> userProgressList = userProgressRepository.findByEnrollmentEnrollId(enrollment.getEnrollId());
                    Map<Long, UserProgress> progressMap = userProgressList.stream()
                            .filter(progress -> progress.getCurriculumItem() != null && progress.getCurriculumItem().getItemId() != null)
                            .collect(Collectors.toMap(progress -> progress.getCurriculumItem().getItemId(), progress -> progress, (left, right) -> left));

                    List<EnrollmentItemResponseDTO> itemResponses = new ArrayList<>();
                    for (int index = 0; index < curriculumItems.size(); index++) {
                        CurriculumItem item = curriculumItems.get(index);
                        UserProgress progress = progressMap.get(item.getItemId());
                        boolean completed = resolveCompleted(progress);
                        int seq = resolveSeq(item, index);
                        Long userProgressId = progress != null ? progress.getProgressId() : null;
                        String videoKey = item.getVideo() != null ? item.getVideo().getVideoKey() : null;
                        itemResponses.add(new EnrollmentItemResponseDTO(userProgressId, videoKey, seq, item.getTitle(), completed));
                    }

                    long completedCount = itemResponses.stream().filter(item -> Boolean.TRUE.equals(item.getIsCompleted())).count();

                    return new EnrollmentListResponseDTO(
                            enrollment.getEnrollId(),
                            curriculum.getCurriId(),
                            pattern != null ? pattern.getTitle() : "",
                            author != null ? author.getNickname() : "",
                            pattern != null ? pattern.getTool() : "",
                            pattern != null ? pattern.getThumbnailUrl() : null,
                            curriculumItems.size(),
                            (int) completedCount,
                            itemResponses
                    );
                })
                .collect(Collectors.toList());
    }

        private boolean resolveCompleted(UserProgress progress) {
            return progress != null && Boolean.TRUE.equals(progress.getIsCompleted());
        }

        private int resolveSeq(CurriculumItem item, int index) {
            Integer stepOrder = item.getStepOrder();
            return stepOrder == null ? index + 1 : stepOrder;
        }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByCurriId(Long curriId) {
        return enrollmentRepository.findByCurriculumCurriId(Objects.requireNonNull(curriId));
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(Objects.requireNonNull(enrollment));
    }

    public void deleteEnrollment(Long enrollId) {
        enrollmentRepository.deleteById(Objects.requireNonNull(enrollId));
    }
}
