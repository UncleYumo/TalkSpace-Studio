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
    GENERATED(1, "已生成"),
    PUBLISHED(2, "已发布");

    private final Integer value;
    private final String desc;

    @Override
    public Integer getValue() {
        return this.value;
    }
}
