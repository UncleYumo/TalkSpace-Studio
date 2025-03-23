package cn.uncleyumo.talkspacestudio.entity.vo;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.entity.pojo.Episode;
import cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest.FinalEpisodeVo;
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
 * @fileName EpisodeVo
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalProjectVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 项目ID
     */
    @NotNull(message = FieldValidateMessageConstant.PROJECT_ID_NOT_NULL)
    private Long projectId;

    /**
     * 内容
     */
    @NotNull(message = FieldValidateMessageConstant.USER_ID_NOT_NULL)
    private Long userId;

    /**
     * 语言类型
     */
    private String language;
    /**
     * 用户提示词
     */
    private String userPrompt;
    /**
     * 总集数
     */
    private Integer episodeCount;
    /**
     * 单集时长（分钟）
     */
    private Integer singleDuration;

    /**
     * 合成的音频
     */
    @NotNull(message = FieldValidateMessageConstant.EPISODE_NOT_NULL)
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<FinalEpisodeVo> episodes;
}
