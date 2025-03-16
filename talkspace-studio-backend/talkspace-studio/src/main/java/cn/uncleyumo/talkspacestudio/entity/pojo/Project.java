package cn.uncleyumo.talkspacestudio.entity.pojo;

import cn.uncleyumo.talkspacestudio.enums.ProjectStatusEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author uncle_yumo
 * @fileName Project
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description 播客项目实体类
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("project")
public class Project implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建者用户id
     */
    private Long userId;
    /**
     * 项目名称
     */
    private String title;
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
     * 项目状态 0-草稿(DRAFT) 1-已生成(GENERATED) 2-已发布(PUBLISHED)
     */
    private ProjectStatusEnum status;
    /**
     * 项目创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 项目更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
