package com.douzone.knitching.domain.pattern.repository;

import com.douzone.knitching.domain.pattern.entity.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {
}
