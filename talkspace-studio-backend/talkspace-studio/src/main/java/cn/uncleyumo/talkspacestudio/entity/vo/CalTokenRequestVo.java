package cn.uncleyumo.talkspacestudio.entity.vo;

import cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest.Result;
import cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest.Usage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author uncle_yumo
 * @fileName CalTokenRequestVo
 * @createDate 2025/3/10 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalTokenRequestVo {
    @JsonProperty("request_id")
    String requestId;
    Integer latency;
    Usage usage;
    Result result;
}