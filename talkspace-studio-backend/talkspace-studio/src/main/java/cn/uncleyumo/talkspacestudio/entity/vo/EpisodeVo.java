package cn.uncleyumo.talkspacestudio.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class EpisodeVo {
    /**
     * 子标题
     */
    @NotBlank(message = "未能解析到有效的子标题")
    private String subTitle;

    @NotNull(message = "未能解析到有效的content")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<EpisodeContentVo> content;
}
