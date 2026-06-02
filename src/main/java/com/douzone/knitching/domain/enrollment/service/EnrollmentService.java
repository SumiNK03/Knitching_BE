package com.douzone.knitching.domain.enrollment.service;

import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import com.douzone.knitching.domain.enrollment.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public Enrollment createEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    @Transactional(readOnly = true)
    public Optional<Enrollment> getEnrollmentById(Long enrollId) {
        return enrollmentRepository.findById(enrollId);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.findByUserUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByCurriId(Long curriId) {
        return enrollmentRepository.findByCurriculumCurriId(curriId);
    }

    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Long enrollId) {
        enrollmentRepository.deleteById(enrollId);
    }
}
