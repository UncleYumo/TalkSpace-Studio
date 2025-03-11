package cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName Result
 * @createDate 2025/3/10 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    @JsonProperty("token_ids")
    List<Integer> tokenIds;
    List<String> tokens;
}