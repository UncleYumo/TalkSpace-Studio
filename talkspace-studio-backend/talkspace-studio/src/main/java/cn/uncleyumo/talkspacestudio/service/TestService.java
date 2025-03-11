package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.entity.vo.CalTokenRequestVo;

/**
 * @author uncle_yumo
 * @fileName TestService
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public interface TestService {

    CalTokenRequestVo countToken(CountTokenDto countTokenDto);

    Integer countTokenNumber(CountTokenDto countTokenDto);
}
