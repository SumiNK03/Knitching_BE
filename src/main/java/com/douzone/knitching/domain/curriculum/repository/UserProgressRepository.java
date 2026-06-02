package com.douzone.knitching.domain.curriculum.repository;

import com.douzone.knitching.domain.curriculum.entity.UserProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    List<UserProgress> findByEnrollmentEnrollId(Long enrollId);
}
