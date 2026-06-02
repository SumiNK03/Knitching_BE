package com.douzone.knitching.domain.video.controller;

import com.douzone.knitching.domain.video.entity.Video;
import com.douzone.knitching.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
@Tag(name = "Video", description = "기술 영상 관리 API")
public class VideoController {
    private final VideoService videoService;

    @Operation(summary = "영상 등록", description = "새로운 영상을 등록합니다")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping
    public ResponseEntity<Video> createVideo(@RequestBody Video video) {
        Video createdVideo = videoService.createVideo(video);
        return ResponseEntity.ok(createdVideo);
    }

    @Operation(summary = "ID로 영상 조회", description = "영상 ID로 영상 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "영상 미존재")
    @GetMapping("/{videoId}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long videoId) {
        return videoService.getVideoById(videoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "기술 코드로 영상 조회", description = "기술 코드로 영상 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @ApiResponse(responseCode = "404", description = "영상 미존재")
    @GetMapping("/tech/{techCode}")
    public ResponseEntity<Video> getVideoByTechCode(@PathVariable String techCode) {
        return videoService.getVideoByTechCode(techCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "전체 영상 조회", description = "모든 영상 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @Operation(summary = "영상 정보 수정", description = "영상 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "수정 성공")
    @PutMapping("/{videoId}")
    public ResponseEntity<Video> updateVideo(@PathVariable Long videoId, @RequestBody Video video) {
        video.setVideoId(videoId);
        Video updatedVideo = videoService.updateVideo(video);
        return ResponseEntity.ok(updatedVideo);
    }

    @Operation(summary = "영상 삭제", description = "영상을 삭제합니다")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{videoId}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long videoId) {
        videoService.deleteVideo(videoId);
        return ResponseEntity.noContent().build();
    }
}
