package com.douzone.knitching.global.aws;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

/**
 * AWS S3 파일 업로드/삭제 서비스
 * - 파일명 UUID로 중복 방지
 * - 폴더 구분: images/, pdf/
 * - 모든 예외는 로깅 후 RuntimeException으로 변환
 * - 프로덕션 환경(aws.s3.enabled=true)에서만 활성화
 */
@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(
        name = "aws.s3.enabled",
        havingValue = "true",
        matchIfMissing = false
)
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket:knitching-storage-2026}")
    private String bucketName;

    /**
     * 파일을 S3에 업로드
     *
     * @param file   업로드할 파일 (MultipartFile)
     * @param folder 폴더명: "images" 또는 "pdf"
     * @return S3 URL
     */
    public String uploadFile(MultipartFile file, String folder) {
        try {
            // 폴더명 유효성 검사
            if (!isValidFolder(folder)) {
                throw new IllegalArgumentException("유효한 폴더명: images, pdf");
            }

            // 원본 파일명 확인
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                throw new IllegalArgumentException("파일명이 없습니다.");
            }

            // 파일 확장자 추출
            String fileExtension = getFileExtension(originalFileName);

            // UUID + 확장자로 새 파일명 생성
            String fileName = UUID.randomUUID() + fileExtension;

            // S3 키: folder/fileName
            String s3Key = folder + "/" + fileName;

            // 파일 크기 가져오기
            long fileSize = file.getSize();

            // S3 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.getContentType())
                    .contentLength(fileSize)
                    .build();

            PutObjectResponse response = s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), fileSize)
            );

            // S3 URL 생성 및 반환
            String s3Url = generateS3Url(s3Key);

            log.info("파일 업로드 성공: {} -> {}", originalFileName, s3Url);
            return s3Url;

        } catch (IOException e) {
            log.error("파일 읽기 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 읽기 중 오류 발생", e);
        } catch (Exception e) {
            log.error("S3 업로드 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 업로드 중 오류 발생", e);
        }
    }

    /**
     * S3의 파일 삭제
     *
     * @param fileUrl 삭제할 파일의 S3 URL
     */
    public void deleteFile(String fileUrl) {
        try {
            // S3 URL에서 객체 키 추출
            String s3Key = extractS3KeyFromUrl(fileUrl);

            if (s3Key == null || s3Key.isEmpty()) {
                throw new IllegalArgumentException("유효하지 않은 S3 URL: " + fileUrl);
            }

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.info("파일 삭제 성공: {}", fileUrl);

        } catch (Exception e) {
            log.error("S3 삭제 실패: {}", e.getMessage(), e);
            throw new RuntimeException("파일 삭제 중 오류 발생", e);
        }
    }

    /**
     * 파일명으로 S3 URL 생성
     * 포맷: https://[bucket].s3.[region].amazonaws.com/[key]
     *
     * @param fileName 파일명 (폴더 포함)
     * @return S3 URL
     */
    public String getFileUrl(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("파일명이 없습니다.");
        }

        return generateS3Url(fileName);
    }

    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return fileName.substring(lastDotIndex).toLowerCase();
        }
        return ""; // 확장자 없음
    }

    /**
     * 유효한 폴더인지 확인
     */
    private boolean isValidFolder(String folder) {
        return folder != null && (folder.equals("images") || folder.equals("pdf"));
    }

    /**
     * S3 URL 생성
     */
    private String generateS3Url(String s3Key) {
        // 버킷 리전을 환경변수에서 가져옴
        String region = System.getenv("AWS_REGION");
        if (region == null) {
            region = "ap-southeast-2"; // 기본값
        }

        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                s3Key
        );
    }

    /**
     * S3 URL에서 객체 키 추출
     * URL 포맷: https://[bucket].s3.[region].amazonaws.com/[key]
     */
    private String extractS3KeyFromUrl(String url) {
        try {
            // "amazonaws.com/" 이후의 부분이 객체 키
            String prefix = "amazonaws.com/";
            int prefixIndex = url.indexOf(prefix);

            if (prefixIndex >= 0) {
                return url.substring(prefixIndex + prefix.length());
            }

            return null;

        } catch (Exception e) {
            log.error("S3 키 추출 실패: {}", url);
            return null;
        }
    }
}
