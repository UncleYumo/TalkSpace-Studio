package cn.uncleyumo.talkspacestudio.service.impl;

import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.entity.vo.CalTokenRequestVo;
import cn.uncleyumo.talkspacestudio.service.TestService;
import cn.uncleyumo.talkspacestudio.utils.LlmTextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uncle_yumo
 * @fileName TestServiceImpl
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
@Slf4j
public class TestServiceImpl implements TestService {

    private LlmTextUtil llmTextUtil;

    @Autowired
    public TestServiceImpl(LlmTextUtil llmTextUtil) {
        this.llmTextUtil = llmTextUtil;
    }

    @Override
    public CalTokenRequestVo countToken(CountTokenDto countTokenDto) {
        return llmTextUtil.countToken(countTokenDto);
    }

    @Override
    public Integer countTokenNumber(CountTokenDto countTokenDto) {
        return llmTextUtil.countToken(countTokenDto).getUsage().getInputTokens();
    }
}
