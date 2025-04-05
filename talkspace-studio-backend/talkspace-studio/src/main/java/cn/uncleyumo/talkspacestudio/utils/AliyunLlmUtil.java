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
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
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
                .resultFormat(GenerationParam.ResultFormat.MESSAGE)  // 结果格式为消息
                .build();
        try {
            GenerationResult generationResult = gen.call(param);
            String content = generationResult.getOutput().getChoices().get(0).getMessage().getContent();
            return JsonUtil.getFirstJsonBlock(content);
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Async
    public void streamOutputGeneration(String prompt, String model, SseEmitter emitter) {
        Message userMsg = Message.builder()
                .role(Role.USER.getValue())
                .content(prompt)
                .build();
        // 启动流式调用，并将结果发送到前端
        try {
            Generation gen = new Generation();
            GenerationParam param = GenerationParam.builder()
                    .apiKey(aliyunLlmProperty.getApiKey())
                    .model(model)
                    .messages(Collections.singletonList(userMsg))
                    .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                    .build();

            Flowable<GenerationResult> results = gen.streamCall(param);
            // 保存 Disposable 对象，Disposable 对象可用于取消订阅
            Disposable subscription = results.subscribe(result -> {
                try {
                    // 将结果转换为JSON并发送
                    String json = JsonUtils.toJson(result);
                    emitter.send(SseEmitter.event().data(json));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }, emitter::completeWithError);  // 出现异常时关闭流

            // 在适当的位置取消订阅，例如在不再需要接收数据时
            // subscription.dispose();
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}
