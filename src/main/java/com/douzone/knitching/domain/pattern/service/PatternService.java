package com.douzone.knitching.domain.pattern.service;

import com.douzone.knitching.domain.pattern.entity.Pattern;
import com.douzone.knitching.domain.pattern.repository.PatternRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PatternService {
    private final PatternRepository patternRepository;

    public Pattern createPattern(Pattern pattern) {
        return patternRepository.save(pattern);
    }

    @Transactional(readOnly = true)
    public Optional<Pattern> getPatternById(Long patternId) {
        return patternRepository.findById(patternId);
    }

    @Transactional(readOnly = true)
    public List<Pattern> getAllPatterns() {
        return patternRepository.findAll();
    }

    public Pattern updatePattern(Pattern pattern) {
        return patternRepository.save(pattern);
    }

    public void deletePattern(Long patternId) {
        patternRepository.deleteById(patternId);
    }
}
