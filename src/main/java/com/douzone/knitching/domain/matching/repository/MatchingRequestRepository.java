package com.douzone.knitching.domain.matching.repository;

import com.douzone.knitching.domain.matching.entity.MatchingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MatchingRequestRepository extends JpaRepository<MatchingRequest, Long> {
    List<MatchingRequest> findByEnrollmentEnrollId(Long enrollId);
    List<MatchingRequest> findByInstructorInstId(Long instId);
}
