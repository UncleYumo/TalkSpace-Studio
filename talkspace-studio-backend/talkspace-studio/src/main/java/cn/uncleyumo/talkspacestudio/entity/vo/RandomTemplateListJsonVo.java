package cn.uncleyumo.talkspacestudio.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName RandomTemplateListJsonVo
 * @createDate 2025/4/5 April
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RandomTemplateListJsonVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private List<GetRandomTemplateVo> randomTemplateList;
}
