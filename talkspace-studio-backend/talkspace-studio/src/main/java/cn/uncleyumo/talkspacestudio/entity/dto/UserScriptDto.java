package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.entity.pojo.ProjectRole;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName UserScriptDto
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScriptDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = FieldValidateMessageConstant.USER_PROMPT_NOT_BLANK)
    private String userPrompt;

    @NotBlank(message = FieldValidateMessageConstant.TITLE_NOT_BLANK)
    private String title;

    @NotBlank(message = FieldValidateMessageConstant.LANGUAGE_NOT_NULL)
    private String language;

    @NotNull(message = FieldValidateMessageConstant.EPISODE_COUNT_NOT_NULL)
    @Min(value = 1, message = FieldValidateMessageConstant.EPISODES_COUNT_MIN_VALUE)
    @Max(value = 10, message = FieldValidateMessageConstant.EPISODES_COUNT_MAX_VALUE)
    private Integer episodeCount;

    @NotNull(message = FieldValidateMessageConstant.ROLES_NOT_NULL)
    List<ProjectRoleDto> roles;

    @NotNull(message = FieldValidateMessageConstant.SINGLE_DURATION_NOT_NULL)
    @Min(value = 1, message = FieldValidateMessageConstant.SINGLE_DURATION_MIN_VALUE)
    @Max(value = 8, message = FieldValidateMessageConstant.SINGLE_DURATION_MAX_VALUE)
    private Integer singleDuration;
}
