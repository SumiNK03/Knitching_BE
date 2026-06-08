package com.douzone.knitching.global.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * AWS S3 클라이언트 설정
 * 환경변수에서 AWS 자격증명 읽음
 * - AWS_ACCESS_KEY_ID
 * - AWS_SECRET_ACCESS_KEY
 * - AWS_REGION
 * 
 * 프로덕션 환경에서만 S3 클라이언트를 생성합니다.
 * 테스트 환경에서는 빈이 생성되지 않습니다.
 */
@Configuration
@Slf4j
public class S3Config {

    @Bean
    @ConditionalOnProperty(
            name = "aws.s3.enabled",
            havingValue = "true",
            matchIfMissing = false
    )
    public S3Client s3Client() {
        String accessKey = System.getenv("AWS_ACCESS_KEY_ID");
        String secretKey = System.getenv("AWS_SECRET_ACCESS_KEY");
        String regionName = System.getenv("AWS_REGION");

        if (accessKey == null || secretKey == null || regionName == null) {
            throw new IllegalStateException(
                    "AWS 환경변수가 설정되지 않았습니다. " +
                    "AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_REGION을 확인하세요.");
        }

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);
        Region region = Region.of(regionName);

        S3Client client = S3Client.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();

        log.info("AWS S3 클라이언트 초기화 완료: bucket region = {}", regionName);

        return client;
    }
}
