package com.douzone.knitching.domain.user.service;

import com.douzone.knitching.domain.user.entity.UserSkill;
import com.douzone.knitching.domain.user.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;

    public UserSkill createUserSkill(UserSkill userSkill) {
        return userSkillRepository.save(userSkill);
    }

    @Transactional(readOnly = true)
    public Optional<UserSkill> getUserSkillById(Long skillId) {
        return userSkillRepository.findById(skillId);
    }

    @Transactional(readOnly = true)
    public List<UserSkill> getUserSkillsByUserId(Long userId) {
        return userSkillRepository.findByUserUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<UserSkill> getAllUserSkills() {
        return userSkillRepository.findAll();
    }

    public UserSkill updateUserSkill(UserSkill userSkill) {
        return userSkillRepository.save(userSkill);
    }

    public void deleteUserSkill(Long skillId) {
        userSkillRepository.deleteById(skillId);
    }
}
