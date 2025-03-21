package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName GenerateAiUserScriptDto
 * @createDate 2025/3/21 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateAiUserScriptDto {
    @NotNull(message = FieldValidateMessageConstant.USER_ID_NOT_NULL)
    private Long userId;
    @NotNull(message = FieldValidateMessageConstant.PROJECT_ID_NOT_NULL)
    private Long projectId;
}
