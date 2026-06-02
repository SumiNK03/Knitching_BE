package com.douzone.knitching.domain.curriculum.repository;

import com.douzone.knitching.domain.curriculum.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
}
