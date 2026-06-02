package com.douzone.knitching.domain.matching.repository;

import com.douzone.knitching.domain.matching.entity.MatchingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatchingRequestRepository extends JpaRepository<MatchingRequest, Long> {
    List<MatchingRequest> findByEnrollmentEnrollId(Long enrollId);
    
    /**
     * instructor 필드가 이제 User를 참조하므로, User의 userId로 조회
     */
    List<MatchingRequest> findByInstructorUserId(Long userId);
}
