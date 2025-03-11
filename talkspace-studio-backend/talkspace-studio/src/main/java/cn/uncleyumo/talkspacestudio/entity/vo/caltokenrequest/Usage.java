package cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName Usage
 * @createDate 2025/3/10 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usage {
    @JsonProperty("input_tokens")
    Integer inputTokens;
}