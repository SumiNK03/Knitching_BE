package com.douzone.knitching.domain.curriculum.service;

import com.douzone.knitching.domain.curriculum.entity.CurriculumItem;
import com.douzone.knitching.domain.curriculum.repository.CurriculumItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CurriculumItemService {
    private final CurriculumItemRepository curriculumItemRepository;

    public CurriculumItem createCurriculumItem(CurriculumItem curriculumItem) {
        return curriculumItemRepository.save(curriculumItem);
    }

    @Transactional(readOnly = true)
    public Optional<CurriculumItem> getCurriculumItemById(Long itemId) {
        return curriculumItemRepository.findById(itemId);
    }

    @Transactional(readOnly = true)
    public List<CurriculumItem> getCurriculumItemsByCurriId(Long curriId) {
        return curriculumItemRepository.findByCurriculumCurriId(curriId);
    }

    @Transactional(readOnly = true)
    public List<CurriculumItem> getAllCurriculumItems() {
        return curriculumItemRepository.findAll();
    }

    public CurriculumItem updateCurriculumItem(CurriculumItem curriculumItem) {
        return curriculumItemRepository.save(curriculumItem);
    }

    public void deleteCurriculumItem(Long itemId) {
        curriculumItemRepository.deleteById(itemId);
    }
}
