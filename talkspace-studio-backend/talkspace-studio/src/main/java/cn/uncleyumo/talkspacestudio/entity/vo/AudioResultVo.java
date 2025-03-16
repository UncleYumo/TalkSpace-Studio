package cn.uncleyumo.talkspacestudio.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * @author uncle_yumo
 * @fileName AudioResultVo
 * @createDate 2025/3/15 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AudioResultVo {
    /**
     * 音频流
     */
    private InputStream audioStream;
    /**
     * 音频时长，单位：秒
     */
    private Integer duration;
}
