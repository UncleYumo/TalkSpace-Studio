package cn.uncleyumo.talkspacestudio.entity.pojo;

import cn.uncleyumo.talkspacestudio.entity.vo.EpisodeContentVo;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName Episode
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("episode")
public class Episode implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 所属项目id
     */
    private Long projectId;
    /**
     * 子标题
     */
    private String subTitle;
    /**
     * 剧本内容
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<EpisodeContentVo> content;
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
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
