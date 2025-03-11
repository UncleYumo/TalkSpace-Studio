package cn.uncleyumo.talkspacestudio.utils;

import cn.uncleyumo.talkspacestudio.constant.AliyunTtsTimbreConstant;
import cn.uncleyumo.talkspacestudio.entity.dto.TtsGenerationParamDto;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author uncle_yumo
 * @fileName AliyunTtsUtil
 * @createDate 2025/3/9 March
 * @school 无锡学院
 * @studentID 22344131
 * @description Aliyun TTS工具类
 * 合成音频的语速: speechRate float default: 1.0 取值范围: 0.5-2.0
 * 合成音频的语调:  pitchRate float default: 1.0 取值范围: 0.5-2.0
 * 合成音频的音量:  volume int default: 50 取值范围: 0-100
 */

@Component
@Slf4j
public class AliyunTtsUtil {

    private final String apiKey;

    @Autowired
    public AliyunTtsUtil(AliyunTtsProperty property) {
        this.apiKey = property.getApiKey();
    }

    /**
     * 根据音色、语速、音量、台词合成音频
     * @param dto 合成参数
     * @return 音频数据流 (InputStream)
     */
    public InputStream text2Speech(TtsGenerationParamDto dto) {
        // 获取语音合成参数
        SpeechSynthesisParam param =
                SpeechSynthesisParam.builder()
                        .apiKey(apiKey)
                        .model(dto.getModel())
                        .voice(dto.getTimbre())
                        .volume(dto.getVolume())
                        .speechRate(dto.getSpeechRate())
                        .pitchRate(dto.getPitchRate())
                        .build();
        // 判断DTO中的音色和TXT是否为空或空字符串
        if (dto.getTimbre() == null || dto.getTimbre().isEmpty()) {
            log.error("!!!Timbre or voice cannot be null or empty.");
            throw new IllegalArgumentException("!!!Timbre or voice cannot be null or empty.");
        }
        // 获取语音合成器
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(param, null);
        // 合成并获取音频数据
        ByteBuffer audio = synthesizer.call(dto.getText());
        String requestId = synthesizer.getLastRequestId();
        // 打印请求ID
        log.info("requestId: " + requestId);
        // 将audio转换为InputStream
        return new ByteArrayInputStream(audio.array());
    }
}
