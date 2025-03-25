package cn.uncleyumo.talkspacestudio.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author uncle_yumo
 * @fileName PublishedProjectDto
 * @createDate 2025/3/25 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishedProjectDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer pageSize;
    private Integer pageNum;

}
