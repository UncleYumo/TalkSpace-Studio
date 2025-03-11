package cn.uncleyumo.talkspacestudio.entity.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName LlmModel
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
public class LlmModel {
    @Schema(description = "模型名称")
    private final String modelName;
    @Schema(description = "模型描述")
    private final String description;
    @Schema(description = "模型最大Token数量")
    private final int maxTokens;
}
