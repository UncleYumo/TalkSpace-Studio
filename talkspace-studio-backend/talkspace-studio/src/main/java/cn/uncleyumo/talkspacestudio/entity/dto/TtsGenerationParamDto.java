package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.AliyunTtsModelConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName TtsGenerationParamDto
 * @createDate 2025/3/10 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TtsGenerationParamDto {
    private String text;
    private String timbre;
    private Float speechRate;
    private Float pitchRate;
    private Integer volume;
    private String model;
}
