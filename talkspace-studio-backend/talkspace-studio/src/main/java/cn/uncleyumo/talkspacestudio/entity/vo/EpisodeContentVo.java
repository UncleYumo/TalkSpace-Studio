package cn.uncleyumo.talkspacestudio.entity.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName EpisodeContentVo
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodeContentVo {
    @NotBlank(message = "未能解析到有效的音效标识：role")
    private String role;
    @NotBlank(message = "未能解析到有效的文本内容：text")
    private String text;
}
