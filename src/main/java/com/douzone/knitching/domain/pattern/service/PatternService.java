package com.douzone.knitching.domain.pattern.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.douzone.knitching.domain.pattern.dto.PatternDetailResponse;
import com.douzone.knitching.domain.pattern.dto.PatternListResponse;
import com.douzone.knitching.domain.pattern.entity.Pattern;
import com.douzone.knitching.domain.pattern.repository.PatternRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PatternService {
    private final PatternRepository patternRepository;

    public Pattern createPattern(@NonNull Pattern pattern) {
        return patternRepository.save(pattern);
    }

    @Transactional(readOnly = true)
    public Page<PatternListResponse> getPatterns(String sort, String tool, @NonNull Pageable pageable) {
        String normalizedTool = (tool == null || "all".equalsIgnoreCase(tool)) ? null : tool;
        if ("popular".equalsIgnoreCase(sort)) {
            return patternRepository.findPatternListPopular(normalizedTool, pageable);
        }
        return patternRepository.findPatternListLatest(normalizedTool, pageable);
    }

    @Transactional(readOnly = true)
    public PatternDetailResponse getPatternById(Long patternId) {
        return patternRepository.findPatternDetailByPatternId(patternId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "도안을 찾을 수 없습니다."));
    }

    public Pattern updatePattern(@NonNull Pattern pattern) {
        return patternRepository.save(pattern);
    }

    public void deletePattern(@NonNull Long patternId) {
        patternRepository.deleteById(patternId);
    }
}
