package cn.uncleyumo.talkspacestudio.utils;

import cn.uncleyumo.talkspacestudio.enums.AliyunLlmModelEnum;
import cn.uncleyumo.talkspacestudio.properties.AliyunLlmProperty;
import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author uncle_yumo
 * @fileName AliyunLlmUtil.java
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description Aliyun LLM工具类
 */

@Component
@Slf4j
public class AliyunLlmUtil {

    private final AliyunLlmProperty aliyunLlmProperty;

    public AliyunLlmUtil(AliyunLlmProperty aliyunLlmProperty) {
        this.aliyunLlmProperty = aliyunLlmProperty;
    }

    public String generate(String prompt, String model) {
        Generation gen = new Generation();
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(prompt)
                .build();
        GenerationParam param = GenerationParam.builder()
                .apiKey(aliyunLlmProperty.getApiKey())
                .model(model)
                .messages(Collections.singletonList(userMsg))
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                .build();
        try {
            GenerationResult generationResult = gen.call(param);
            String content = generationResult.getOutput().getChoices().get(0).getMessage().getContent();
            return JsonUtil.getFirstJsonBlock(content);
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
