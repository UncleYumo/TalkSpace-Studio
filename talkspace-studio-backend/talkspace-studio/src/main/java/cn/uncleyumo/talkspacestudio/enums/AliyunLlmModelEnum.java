package cn.uncleyumo.talkspacestudio.enums;

import cn.uncleyumo.talkspacestudio.entity.pojo.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName AliyunLlmModelEnum.java
 * @createDate 2025/3/8 March
 * @school 无锡学院
 * @studentID 22344131
 * @description Aliyun Llm Model Enum
 */

public enum AliyunLlmModelEnum {
    QWEN_MAX("qwen-max", "适合复杂任务，推理能力最强", 32768),
    QWEN_PLUS("qwen-plus", "效果、速度、成本均衡", 131072),
    QWEN_TURBO("qwen-turbo", "适合简单任务，速度快、成本极低", 1000000),
    QWEN_LONG("qwen-long", "适合大规模文本分析，效果与速度均衡、成本较低", 10000000);

    private final String modelName;
    private final String description;
    private final int maxTokens;

    AliyunLlmModelEnum(String modelName, String description, int maxTokens) {
        this.modelName = modelName;
        this.description = description;
        this.maxTokens = maxTokens;
    }

    public static List<LlmModel> getModelList() {
        ArrayList<LlmModel> list = new ArrayList<>();
        for (AliyunLlmModelEnum model : AliyunLlmModelEnum.values()) {
            list.add(new LlmModel(model.modelName, model.description, model.maxTokens));
        }
        return list;
    }
}
