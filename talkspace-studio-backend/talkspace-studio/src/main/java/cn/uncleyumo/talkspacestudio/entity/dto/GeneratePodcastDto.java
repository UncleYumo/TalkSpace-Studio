package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.entity.vo.EpisodeVo;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName GenerateEpisodesDto
 * @createDate 2025/3/15 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneratePodcastDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = FieldValidateMessageConstant.PROJECT_ID_NOT_NULL)
    private Long projectId;

    @NotNull(message = FieldValidateMessageConstant.SINGLE_RATE_NOT_NULL)
    @DecimalMax(value = "2.0", message = FieldValidateMessageConstant.SINGLE_RATE_MAX_VALUE)
    @DecimalMin(value = "0.5", message = FieldValidateMessageConstant.SINGLE_RATE_MIN_VALUE)
    private Float speechRate;

    @NotNull(message = FieldValidateMessageConstant.SINGLE_PITCH_NOT_NULL)
    @DecimalMax(value = "2.0", message = FieldValidateMessageConstant.SINGLE_PITCH_MAX_VALUE)
    @DecimalMin(value = "0.5", message = FieldValidateMessageConstant.SINGLE_PITCH_MIN_VALUE)
    private Float pitchRate;

    @NotNull(message = FieldValidateMessageConstant.SINGLE_VOLUME_NOT_NULL)
    @Max(value = 100, message = FieldValidateMessageConstant.SINGLE_VOLUME_MAX_VALUE)
    @Min(value = 0, message = FieldValidateMessageConstant.SINGLE_VOLUME_MIN_VALUE)
    private Integer volume;

    @NotNull(message = FieldValidateMessageConstant.EPISODE_NOT_NULL)
    private List<EpisodeVo> episodes;
}
