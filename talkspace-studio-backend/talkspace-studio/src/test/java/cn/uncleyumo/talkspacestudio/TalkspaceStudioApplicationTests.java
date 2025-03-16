package cn.uncleyumo.talkspacestudio;

import cn.uncleyumo.talkspacestudio.constant.AliyunTtsModelConstant;
import cn.uncleyumo.talkspacestudio.entity.dto.TtsGenerationParamDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.TtsTimbre;
import cn.uncleyumo.talkspacestudio.enums.AliyunLlmModelEnum;
import cn.uncleyumo.talkspacestudio.enums.AliyunTtsTimbreEnum;
import cn.uncleyumo.talkspacestudio.utils.AliyunTtsUtil;
import cn.uncleyumo.talkspacestudio.utils.LlmTextUtil;
import cn.uncleyumo.talkspacestudio.utils.MinioUtil;
import cn.uncleyumo.talkspacestudio.utils.YumoColorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
class TalkspaceStudioApplicationTests {

    private final MinioUtil minioUtil;
    private final AliyunTtsUtil aliyunTtsUtil;
    private final LlmTextUtil llmTextUtil;

    private final PasswordEncoder passwordEncoder;
    private final YumoColorPrintUtil yumoColorPrintUtil;

    @Autowired
    public TalkspaceStudioApplicationTests(MinioUtil minioUtil, AliyunTtsUtil aliyunTtsUtil, LlmTextUtil llmTextUtil, PasswordEncoder passwordEncoder, YumoColorPrintUtil yumoColorPrintUtil) {
        this.minioUtil = minioUtil;
        this.aliyunTtsUtil = aliyunTtsUtil;
        this.llmTextUtil = llmTextUtil;
        this.passwordEncoder = passwordEncoder;
        this.yumoColorPrintUtil = yumoColorPrintUtil;
    }

//    @Test
    void securityTest() {
        String password = "password";
        List<String> encodedPasswordList = Arrays.asList(
                passwordEncoder.encode(password),
                passwordEncoder.encode(password),
                passwordEncoder.encode(password),
                passwordEncoder.encode(password),
                passwordEncoder.encode(password)
        );
        log.info("Encoded password list: {}", encodedPasswordList);
        for (String encodedPassword : encodedPasswordList) {
            log.info("Password: {}, Match: {}", encodedPassword.substring(0, 10) + "......", passwordEncoder.matches(password, encodedPassword));
        }
        yumoColorPrintUtil.printlnCyanBlack(encodedPasswordList.get(0));
    }

    @Test
    void testLocalDateTime() {
        // 时间格式为：2021-08-17T16:20:10.000Z
        System.out.println(LocalDateTime.now());
        // 转换成指定格式：2021-08-17 16:20:10
    }

    @Test
    void testTtsGeneration() {
        TtsGenerationParamDto paramDto = new TtsGenerationParamDto();
        paramDto.setText("你好，欢迎使用Talkspace Studio。我是王圣hhhhhhh");
        String timbreName = AliyunTtsTimbreEnum.LONG_WAN.getTimbreName();
        paramDto.setTimbre(timbreName);
        paramDto.setSpeechRate(1.0f);
        paramDto.setPitchRate(1.0f);
        paramDto.setVolume(70);
        paramDto.setModel(AliyunTtsModelConstant.COSY_VOICE_V1);
        InputStream inputStream = aliyunTtsUtil.text2Speech(paramDto);
        String fileUrl = minioUtil.uploadFile(inputStream, "test.mp3", "audio/mp3");
        yumoColorPrintUtil.printlnCyanBlack(fileUrl);
    }
}