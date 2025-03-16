package cn.uncleyumo.talkspacestudio.utils;

import cn.uncleyumo.talkspacestudio.entity.vo.CalTokenRequestVo;
import cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest.Messages;
import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.properties.AliyunLlmProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author uncle_yumo
 * @fileName LlmTextUtil.java
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Component
@Slf4j
public class LlmTextUtil {
    private final AliyunLlmProperty property;
    private final RestTemplate restTemplate;

    @Autowired
    public LlmTextUtil(AliyunLlmProperty property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
    }

    public CalTokenRequestVo countToken(CountTokenDto dto) {
        CalTokenRequestVo result = null;
        try {
            String calTokenBaseUrl = property.getCalTokenBaseUrl() + dto.getModel() + "/tokenizer";
            Map<String, Object> requestBody = new HashMap<>();
            List<Messages> messages = new ArrayList<>();
            messages.add(new Messages(dto.getRole(), dto.getContent()));
            requestBody.put("messages", messages);

            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            requestHeaders.set("Authorization", "Bearer " + property.getApiToken());

            HttpEntity<Map<String, Object>> r = new HttpEntity<Map<String, Object>>(requestBody, requestHeaders);
            result = restTemplate.postForObject(calTokenBaseUrl, r, CalTokenRequestVo.class);
            log.info("响应(result)：{}", result);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
