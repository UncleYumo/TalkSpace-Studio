package cn.uncleyumo.talkspacestudio.entity.pojo;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.entity.vo.EpisodeVo;
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
 * @fileName UserScript
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserScript implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = FieldValidateMessageConstant.TITLE_NOT_BLANK)
    private String title;
    @NotNull(message = FieldValidateMessageConstant.EPISODE_NOT_NULL)
    private List<EpisodeVo> episodes;
}
