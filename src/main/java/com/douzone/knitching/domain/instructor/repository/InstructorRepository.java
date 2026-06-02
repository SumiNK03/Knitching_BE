package com.douzone.knitching.domain.instructor.repository;

import com.douzone.knitching.domain.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // loginId와 email은 이제 User 엔티티에만 존재하므로,
    // User를 통해 조회 후 Instructor를 접근해야 함
}
