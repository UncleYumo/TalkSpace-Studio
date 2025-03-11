package cn.uncleyumo.talkspacestudio.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author uncle_yumo
 * @fileName AliyunLlmProperty
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.llm")
public class AliyunLlmProperty {
    private String apiKey;
    private String calTokenBaseUrl;
}
