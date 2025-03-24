package cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest;

import cn.uncleyumo.talkspacestudio.entity.vo.EpisodeContentVo;
import cn.uncleyumo.talkspacestudio.entity.vo.EpisodeContentWithCharacterNameVo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName FinalEpisodeVo
 * @createDate 2025/3/22 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalEpisodeVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 子标题
     */
    private String subTitle;
    /**
     * 剧本内容
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<EpisodeContentWithCharacterNameVo> content;
    /**
     * 音频时常（秒）
     */
    private Integer duration;
    /**
     * 音频文件地址
     */
    private String audioUrl;
    /**
     * 集数顺序
     */
    private Integer sequence;
}
