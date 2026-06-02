package com.douzone.knitching.domain.instructor.repository;

import com.douzone.knitching.domain.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByLoginId(String loginId);
    Optional<Instructor> findByEmail(String email);
}
