package cn.uncleyumo.talkspacestudio.utils;

import cn.uncleyumo.talkspacestudio.constant.AliyunTtsTimbreConstant;
import cn.uncleyumo.talkspacestudio.properties.FilePathProperty;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * @author uncle_yumo
 * @fileName AliyunTtsUtilTest
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@SpringBootTest
@Slf4j
class AliyunTtsUtilTest {

    @Autowired
    private AliyunTtsProperty property;

    @Test
    void testTtsToFile() {
    }
}