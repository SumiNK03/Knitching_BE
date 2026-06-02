package com.douzone.knitching.domain.curriculum.service;

import com.douzone.knitching.domain.curriculum.entity.Curriculum;
import com.douzone.knitching.domain.curriculum.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CurriculumService {
    private final CurriculumRepository curriculumRepository;

    public Curriculum createCurriculum(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    @Transactional(readOnly = true)
    public Optional<Curriculum> getCurriculumById(Long curriId) {
        return curriculumRepository.findById(curriId);
    }

    @Transactional(readOnly = true)
    public List<Curriculum> getAllCurriculums() {
        return curriculumRepository.findAll();
    }

    public Curriculum updateCurriculum(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    public void deleteCurriculum(Long curriId) {
        curriculumRepository.deleteById(curriId);
    }
}
