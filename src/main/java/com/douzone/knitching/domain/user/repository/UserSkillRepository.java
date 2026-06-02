package com.douzone.knitching.domain.user.repository;

import com.douzone.knitching.domain.user.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findByUserUserId(Long userId);
}
