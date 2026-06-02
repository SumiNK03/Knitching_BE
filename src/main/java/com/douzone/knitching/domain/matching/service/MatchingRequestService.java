package com.douzone.knitching.domain.matching.service;

import com.douzone.knitching.domain.matching.entity.MatchingRequest;
import com.douzone.knitching.domain.matching.repository.MatchingRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MatchingRequestService {
    private final MatchingRequestRepository matchingRequestRepository;

    public MatchingRequest createMatchingRequest(MatchingRequest matchingRequest) {
        return matchingRequestRepository.save(matchingRequest);
    }

    @Transactional(readOnly = true)
    public Optional<MatchingRequest> getMatchingRequestById(Long matchId) {
        return matchingRequestRepository.findById(matchId);
    }

    @Transactional(readOnly = true)
    public List<MatchingRequest> getMatchingRequestsByEnrollId(Long enrollId) {
        return matchingRequestRepository.findByEnrollmentEnrollId(enrollId);
    }

    @Transactional(readOnly = true)
    public List<MatchingRequest> getMatchingRequestsByInstId(Long instId) {
        return matchingRequestRepository.findByInstructorInstId(instId);
    }

    @Transactional(readOnly = true)
    public List<MatchingRequest> getAllMatchingRequests() {
        return matchingRequestRepository.findAll();
    }

    public MatchingRequest updateMatchingRequest(MatchingRequest matchingRequest) {
        return matchingRequestRepository.save(matchingRequest);
    }

    public void deleteMatchingRequest(Long matchId) {
        matchingRequestRepository.deleteById(matchId);
    }
}
