package cn.uncleyumo.talkspacestudio;

import cn.hutool.core.lang.Console;
import cn.uncleyumo.talkspacestudio.constant.AliyunLlmModelConstant;
import cn.uncleyumo.talkspacestudio.constant.LlmPromptConstant;
import cn.uncleyumo.talkspacestudio.utils.AliyunTtsUtil;
import cn.uncleyumo.talkspacestudio.utils.LlmTextUtil;
import cn.uncleyumo.talkspacestudio.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class TalkspaceStudioApplicationTests {

    private MinioUtil minioUtil;
    private AliyunTtsUtil aliyunTtsUtil;
    private LlmTextUtil llmTextUtil;

    @Autowired
    public TalkspaceStudioApplicationTests(MinioUtil minioUtil, AliyunTtsUtil aliyunTtsUtil, LlmTextUtil llmTextUtil) {
        this.minioUtil = minioUtil;
        this.aliyunTtsUtil = aliyunTtsUtil;
        this.llmTextUtil = llmTextUtil;
    }
}
