package cn.uncleyumo.talkspacestudio.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author uncle_yumo
 * @fileName AiGenerateUserPromptVo
 * @createDate 2025/4/4 April
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRandomTemplateVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private String userPrompt;
}
