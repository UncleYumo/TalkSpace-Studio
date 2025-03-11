package cn.uncleyumo.talkspacestudio.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author uncle_yumo
 * @fileName MinioProperty
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperty {
    private String endpoint;
    private String fileHost;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
