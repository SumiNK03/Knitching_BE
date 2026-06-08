package com.douzone.knitching.domain.curriculum.service;

import com.douzone.knitching.domain.curriculum.dto.CompleteUserProgressResponseDTO;
import com.douzone.knitching.domain.curriculum.entity.UserProgress;
import com.douzone.knitching.domain.curriculum.repository.UserProgressRepository;
import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public UserProgress createUserProgress(UserProgress userProgress) {
        return userProgressRepository.save(Objects.requireNonNull(userProgress));
    }

    @Transactional(readOnly = true)
    public Optional<UserProgress> getUserProgressById(Long progressId) {
        return userProgressRepository.findById(Objects.requireNonNull(progressId));
    }

    @Transactional(readOnly = true)
    public List<UserProgress> getUserProgressByEnrollId(Long enrollId) {
        return userProgressRepository.findByEnrollmentEnrollId(enrollId);
    }

    @Transactional(readOnly = true)
    public List<UserProgress> getAllUserProgress() {
        return userProgressRepository.findAll();
    }

    public CompleteUserProgressResponseDTO completeUserProgress(Long progressId) {
        UserProgress userProgress = userProgressRepository.findById(Objects.requireNonNull(progressId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "학습 진도를 찾을 수 없습니다."));

        userProgress.setIsCompleted(true);
        userProgress.setCompletedAt(LocalDateTime.now());

        UserProgress savedProgress = userProgressRepository.save(userProgress);

        Enrollment enrollment = savedProgress.getEnrollment();
        if (enrollment != null && enrollment.getEnrollId() != null) {
            List<UserProgress> progressList = userProgressRepository.findByEnrollmentEnrollId(enrollment.getEnrollId());
            boolean allCompleted = progressList.stream().allMatch(progress -> Boolean.TRUE.equals(progress.getIsCompleted()));
            if (allCompleted) {
                enrollment.setStatus(Enrollment.EnrollmentStatus.DONE);
            }
        }

        return new CompleteUserProgressResponseDTO(
                savedProgress.getProgressId(),
                savedProgress.getEnrollment() != null ? savedProgress.getEnrollment().getEnrollId() : null,
                savedProgress.getCurriculumItem() != null ? savedProgress.getCurriculumItem().getItemId() : null,
                savedProgress.getIsCompleted(),
                savedProgress.getCompletedAt() != null ? savedProgress.getCompletedAt().format(DATE_TIME_FORMATTER) : null,
                savedProgress.getEnrollment() != null && savedProgress.getEnrollment().getStatus() != null
                        ? savedProgress.getEnrollment().getStatus().name()
                        : null
        );
    }

    public UserProgress updateUserProgress(UserProgress userProgress) {
        return userProgressRepository.save(Objects.requireNonNull(userProgress));
    }

    public void deleteUserProgress(Long progressId) {
        userProgressRepository.deleteById(Objects.requireNonNull(progressId));
    }
}
