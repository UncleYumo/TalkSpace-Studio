package cn.uncleyumo.talkspacestudio.utils;

import cn.uncleyumo.talkspacestudio.properties.MinioProperty;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.InputStream;

/**
 * @author uncle_yumo
 * @fileName MinioUtil
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Component
@Slf4j
public class MinioUtil {
    private MinioClient minioClient;
    private MinioProperty minioProperty;

    @Autowired
    public MinioUtil(MinioClient minioClient, MinioProperty minioProperty) {
        this.minioClient = minioClient;
        this.minioProperty = minioProperty;
    }

    public String uploadFile(InputStream inputStream, String objectName, String contentType) throws Exception {
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioProperty.getBucketName())
                    .object(objectName)
                    .stream(inputStream, -1, 10485760) // 设置分块大小为10MB
                    .contentType(contentType)
                    .build();
            minioClient.putObject(args);
            return minioProperty.getFileHost() + "/" + minioProperty.getBucketName() + "/" + objectName;
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败", e);
        }
    }
}
