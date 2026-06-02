package com.douzone.knitching.domain.curriculum.repository;

import com.douzone.knitching.domain.curriculum.entity.CurriculumItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CurriculumItemRepository extends JpaRepository<CurriculumItem, Long> {
    List<CurriculumItem> findByCurriculumCurriId(Long curriId);
}
