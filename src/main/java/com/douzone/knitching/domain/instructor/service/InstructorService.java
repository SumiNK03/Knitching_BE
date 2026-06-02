package com.douzone.knitching.domain.instructor.service;

import com.douzone.knitching.domain.instructor.entity.Instructor;
import com.douzone.knitching.domain.instructor.repository.InstructorRepository;
import com.douzone.knitching.domain.user.entity.User;
import com.douzone.knitching.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final UserRepository userRepository;

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Transactional(readOnly = true)
    public Optional<Instructor> getInstructorById(Long instId) {
        return instructorRepository.findById(instId);
    }

    /**
     * 로그인 ID로 강사 정보 조회
     * Instructor는 User와의 식별 관계이므로, User를 먼저 찾은 후 Instructor 접근
     */
    @Transactional(readOnly = true)
    public Optional<Instructor> getInstructorByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .flatMap(user -> user.getInstructor() != null 
                    ? Optional.of(user.getInstructor()) 
                    : Optional.empty());
    }

    /**
     * 이메일로 강사 정보 조회
     * Instructor는 User와의 식별 관계이므로, User를 먼저 찾은 후 Instructor 접근
     */
    @Transactional(readOnly = true)
    public Optional<Instructor> getInstructorByEmail(String email) {
        return userRepository.findByEmail(email)
                .flatMap(user -> user.getInstructor() != null 
                    ? Optional.of(user.getInstructor()) 
                    : Optional.empty());
    }

    @Transactional(readOnly = true)
    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor updateInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public void deleteInstructor(Long instId) {
        instructorRepository.deleteById(instId);
    }
}
