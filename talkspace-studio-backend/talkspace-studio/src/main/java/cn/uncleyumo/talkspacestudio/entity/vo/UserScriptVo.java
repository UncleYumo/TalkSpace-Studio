package cn.uncleyumo.talkspacestudio.entity.vo;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
public class UserScriptVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @NotNull(message = FieldValidateMessageConstant.PROJECT_TITLE_NOT_BLANK)
    private String title;
    @NotNull(message = FieldValidateMessageConstant.EPISODE_NOT_NULL)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<EpisodeWithCharacterNameVo> episodesWithCharacterName;
}
