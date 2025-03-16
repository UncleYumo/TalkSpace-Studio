package cn.uncleyumo.talkspacestudio.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author uncle_yumo
 * @fileName ProjectRoleDto
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRoleDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 音色标识
     */
    private String timbre;
    /**
     * 角色名称
     */
    private String characterName;
}
