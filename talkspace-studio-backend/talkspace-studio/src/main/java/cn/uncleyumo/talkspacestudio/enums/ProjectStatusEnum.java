package cn.uncleyumo.talkspacestudio.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

/**
 * @author uncle_yumo
 * @fileName ProjectStatusEnum
 * @createDate 2025/3/15 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Getter
@ToString
@AllArgsConstructor
public enum ProjectStatusEnum implements IEnum<Integer> {

    DRAFT(0, "草稿"),
    DRAFT_SCRIPT(1, "剧本生成中"),
    SCRIPT(2, "剧本"),
    PODCAST_SCRIPT(3, "播客生成中"),
    PODCAST(4, "播客"),
    PUBLISHED(5, "已发布");

    private final Integer value;
    private final String desc;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
