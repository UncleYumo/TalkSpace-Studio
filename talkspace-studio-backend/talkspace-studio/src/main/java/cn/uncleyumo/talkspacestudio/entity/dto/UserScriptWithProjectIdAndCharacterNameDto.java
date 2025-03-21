package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptVo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author uncle_yumo
 * @fileName UserScriptWithProjectIdVo
 * @createDate 2025/3/15 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScriptWithProjectIdAndCharacterNameDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = FieldValidateMessageConstant.PROJECT_ID_NOT_NULL)
    private Long projectId;
    @NotNull(message = FieldValidateMessageConstant.USER_SCRIPT_NOT_NULL)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private UserScriptVo userScriptWithCharacterName;
}
