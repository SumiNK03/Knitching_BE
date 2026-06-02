package com.douzone.knitching.domain.curriculum.service;

import com.douzone.knitching.domain.curriculum.entity.UserProgress;
import com.douzone.knitching.domain.curriculum.repository.UserProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProgressService {
    private final UserProgressRepository userProgressRepository;

    public UserProgress createUserProgress(UserProgress userProgress) {
        return userProgressRepository.save(userProgress);
    }

    @Transactional(readOnly = true)
    public Optional<UserProgress> getUserProgressById(Long progressId) {
        return userProgressRepository.findById(progressId);
    }

    @Transactional(readOnly = true)
    public List<UserProgress> getUserProgressByEnrollId(Long enrollId) {
        return userProgressRepository.findByEnrollmentEnrollId(enrollId);
    }

    @Transactional(readOnly = true)
    public List<UserProgress> getAllUserProgress() {
        return userProgressRepository.findAll();
    }

    public UserProgress updateUserProgress(UserProgress userProgress) {
        return userProgressRepository.save(userProgress);
    }

    public void deleteUserProgress(Long progressId) {
        userProgressRepository.deleteById(progressId);
    }
}
