package cn.uncleyumo.talkspacestudio.entity.temp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName CountTokenDto
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountTokenDto {
    @Schema(description = "角色")
    private String role;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "llm模型")
    private String  model;
}
