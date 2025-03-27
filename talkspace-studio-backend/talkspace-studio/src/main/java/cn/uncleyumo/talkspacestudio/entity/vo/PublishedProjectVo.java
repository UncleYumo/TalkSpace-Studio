package cn.uncleyumo.talkspacestudio.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author uncle_yumo
 * @fileName PublishedProjectVo
 * @createDate 2025/3/25 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishedProjectVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 播客项目id
     */
    private Long projectId;

    /**
     * 创建者用户id
     */
    private Long userId;
    /**
     * 头像url
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 性别
     */
    private Integer gender;  // 1:male 0:female
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
     * 收藏数
     */
    private Long collectionCount;
    /**
     * 是否被当前用户收藏
     */
    private Boolean isCollected;
    /**
     * 发布时间
     */
    private LocalDateTime publishedTime;
}
