package com.douzone.knitching.domain.enrollment.repository;

import com.douzone.knitching.domain.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByUserUserId(Long userId);
    List<Enrollment> findByUserUserIdOrderByCreatedAtDesc(Long userId);
    List<Enrollment> findByCurriculumCurriId(Long curriId);
    boolean existsByUserUserIdAndCurriculumPatternPatternId(Long userId, Long patternId);
}
