package com.douzone.knitching.domain.video.service;

import com.douzone.knitching.domain.video.entity.Video;
import com.douzone.knitching.domain.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoService {
    private final VideoRepository videoRepository;

    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Transactional(readOnly = true)
    public Optional<Video> getVideoById(Long videoId) {
        return videoRepository.findById(videoId);
    }

    @Transactional(readOnly = true)
    public Optional<Video> getVideoByTechCode(String techCode) {
        return videoRepository.findByTechCode(techCode);
    }

    @Transactional(readOnly = true)
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public Video updateVideo(Video video) {
        return videoRepository.save(video);
    }

    public void deleteVideo(Long videoId) {
        videoRepository.deleteById(videoId);
    }
}
