package com.douzone.knitching.domain.video.repository;

import com.douzone.knitching.domain.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByTechCode(String techCode);
}
