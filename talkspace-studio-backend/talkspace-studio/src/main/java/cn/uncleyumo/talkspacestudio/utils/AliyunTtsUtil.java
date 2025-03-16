package cn.uncleyumo.talkspacestudio.utils;

import cn.uncleyumo.talkspacestudio.constant.AliyunTtsModelConstant;
import cn.uncleyumo.talkspacestudio.constant.AliyunTtsTimbreConstant;
import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.entity.dto.TtsGenerationParamDto;
import cn.uncleyumo.talkspacestudio.entity.vo.AudioResultVo;
import cn.uncleyumo.talkspacestudio.entity.vo.EpisodeContentVo;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesizer;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.List;

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
        log.warn("audio {}", audio);
        String requestId = synthesizer.getLastRequestId();
        // 打印请求ID
        log.info("requestId: " + requestId);
        // 将audio转换为InputStream
        return new ByteArrayInputStream(audio.array());
    }

public AudioResultVo episodes2Speech(List<EpisodeContentVo> episodes, SpeechSynthesisParam param) {

    // 创建合并音频的缓冲区
    ByteArrayOutputStream mergedAudio = new ByteArrayOutputStream();

    try {
        // 遍历每个分集内容
        for (EpisodeContentVo episode : episodes) {
            // 设置当前角色音色
            param.setVoice(episode.getRole());

            // 生成单段音频流
            try (InputStream is = paramWithText2Speech(param, episode.getText())) {
                // 读取音频字节流
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    mergedAudio.write(buffer, 0, bytesRead);
                }
            }
        }
    } catch (IOException e) {
        log.error("音频合成失败: {}", e.getMessage());
        throw new RuntimeException(CommonErrorMessage.IO_ERROR);
    }

    byte[] audioBytes = mergedAudio.toByteArray();
    Integer duration = getAudioDuration(audioBytes);

    return new AudioResultVo(
            new ByteArrayInputStream(audioBytes),
            duration
    );
}

// 辅助方法：读取完整字节流
private byte[] readAllBytes(InputStream is) {
    try {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int nRead;
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    } catch (IOException e) {
        throw new RuntimeException(CommonErrorMessage.IO_ERROR);
    }
}

    public InputStream paramWithText2Speech(SpeechSynthesisParam param, String text) {
        // 判断DTO中的音色和TXT是否为空或空字符串
        if (param.getVoice() == null || param.getVoice().isEmpty()) {
            throw new IllegalArgumentException(CommonErrorMessage.TIMBRE_OR_VOICE_CANNOT_BE_NULL_OR_EMPTY);
        }
        // 获取语音合成器
        SpeechSynthesizer synthesizer = new SpeechSynthesizer(param, null);
        // 合成并获取音频数据
        ByteBuffer audio = synthesizer.call(text);
        log.warn("audio {}", audio);
        String requestId = synthesizer.getLastRequestId();
        // 打印请求ID
        log.info("requestId: " + requestId);
        // 将audio转换为InputStream
        return new ByteArrayInputStream(audio.array());
    }

    // 修改后的方法
//    public static Integer getAudioDuration(byte[] audioData) {
//        File tempFile = null;
//        try {
//            // 创建临时文件
//            tempFile = File.createTempFile("audio", ".mp3");
//            Files.write(tempFile.toPath(), audioData);
//
//            // 使用文件路径构造Mp3File
//            Mp3File mp3File = new Mp3File(tempFile.getAbsolutePath());
//            return (int) (mp3File.getLengthInMilliseconds() / 1000);
//        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
//            log.error("获取音频时长失败: {}", e.getMessage());
//            throw new RuntimeException(CommonErrorMessage.IO_ERROR);
//        } finally {
//            // 清理临时文件
//            if (tempFile != null && !tempFile.delete()) {
//                log.warn("临时文件删除失败: {}", tempFile.getAbsolutePath());
//            }
//        }
//    }

    public static Integer getAudioDuration(byte[] audioData) {
        // 固定比特率256kbps = 32KB/s
        double duration = (audioData.length / 32.0) / 1024.0;
        return (int) Math.round(duration);
    }
}
