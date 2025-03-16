package cn.uncleyumo.talkspacestudio.entity.pojo;

/**
 * @author uncle_yumo
 * @fileName TtsModel
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

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
public class TtsTimbre implements Serializable {

    @Serial  // 序列化标识符
    private static final long serialVersionUID = 1L;

    @Schema(description = "模型名称")
    private final String timbreName;
    @Schema(description = "模型描述")
    private final String description;
    @Schema(description = "模型支持语言")
    private final String language;
}
