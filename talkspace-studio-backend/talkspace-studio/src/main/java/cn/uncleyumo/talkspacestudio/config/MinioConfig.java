package cn.uncleyumo.talkspacestudio.config;

import cn.uncleyumo.talkspacestudio.properties.MinioProperty;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uncle_yumo
 * @fileName MinioConfig
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@Configuration
@Slf4j
public class MinioConfig {

    @Autowired
    private MinioProperty minioProperty;

    @Bean
    public MinioClient minioClient() {
        log.info("Creating MinioClient");


        MinioClient minioClient = MinioClient.builder()
                .endpoint(minioProperty.getEndpoint())
                .credentials(minioProperty.getAccessKey(), minioProperty.getSecretKey())
                .build();

        // Check and create bucket if not exists
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperty.getBucketName()).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperty.getBucketName()).build());
                log.info("Bucket {} created.", minioProperty.getBucketName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Failed to create bucket " + minioProperty.getBucketName());
        }

        return minioClient;
    }
}
