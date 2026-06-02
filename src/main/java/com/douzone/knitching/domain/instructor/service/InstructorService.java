package com.douzone.knitching.domain.instructor.service;

import com.douzone.knitching.domain.instructor.entity.Instructor;
import com.douzone.knitching.domain.instructor.repository.InstructorRepository;
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

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Transactional(readOnly = true)
    public Optional<Instructor> getInstructorById(Long instId) {
        return instructorRepository.findById(instId);
    }

    @Transactional(readOnly = true)
    public Optional<Instructor> getInstructorByLoginId(String loginId) {
        return instructorRepository.findByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public Optional<Instructor> getInstructorByEmail(String email) {
        return instructorRepository.findByEmail(email);
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
