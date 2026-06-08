package com.douzone.knitching.domain.curriculum.repository;

import com.douzone.knitching.domain.curriculum.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {
	Optional<Curriculum> findFirstByPatternPatternIdOrderByCurriIdAsc(Long patternId);
}
