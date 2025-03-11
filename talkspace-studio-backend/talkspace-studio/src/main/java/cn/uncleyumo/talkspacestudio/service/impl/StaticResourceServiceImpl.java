package cn.uncleyumo.talkspacestudio.service.impl;

import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.entity.vo.CalTokenRequestVo;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import cn.uncleyumo.talkspacestudio.service.StaticResourceService;
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
public class StaticResourceServiceImpl implements StaticResourceService {

    private AliyunTtsProperty property;

    @Autowired
    public StaticResourceServiceImpl(AliyunTtsProperty property) {
        this.property = property;
    }

    @Override
    public Integer getTtsMaxTokens() {
        return property.getMaxTokens();
    }
}
