package cn.uncleyumo.talkspacestudio.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName ProjectRoleVo
 * @createDate 2025/3/21 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRoleVo {
    /**
     * 音色标识
     */
    private String role;
    /**
     * 角色名称
     */
    private String characterName;
}
