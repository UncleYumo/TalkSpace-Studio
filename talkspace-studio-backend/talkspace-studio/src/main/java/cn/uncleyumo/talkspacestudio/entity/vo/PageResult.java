package cn.uncleyumo.talkspacestudio.entity.vo;

import lombok.*;

/**
 * @author uncle_yumo
 * @fileName PageResult
 * @createDate 2025/3/25 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PUBLIC) // 显式声明构建器为public
public class PageResult <T> {
    private int pageNum;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private T records;
}
