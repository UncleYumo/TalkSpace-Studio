package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.util.SaResult;
import cn.uncleyumo.talkspacestudio.enums.AliyunLlmModelEnum;
import cn.uncleyumo.talkspacestudio.service.StaticResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uncle_yumo
 * @fileName StaticResourceController
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@RestController
@Tag(name = "静态资源控制器")
@Slf4j
@RequestMapping("/static_resource")
public class StaticResourceController {

    private StaticResourceService staticResourceService;

    @Autowired
    public StaticResourceController(StaticResourceService staticResourceService) {
        this.staticResourceService = staticResourceService;
    }

    @GetMapping("/llm_model_List")
    @Operation(summary = "获取llm模型列表")
    public SaResult llmModelList() {
        log.info("获取llm模型列表");
        return SaResult.data(AliyunLlmModelEnum.getModelList());
    }

    @GetMapping("/tts_max_tokens")
    @Operation(summary = "获取tts最大文本长度")
    public SaResult ttsMaxTokens() {
        log.info("获取tts最大文本长度");
        Integer maxTokens = staticResourceService.getTtsMaxTokens();
        return SaResult.data(maxTokens);
    }
}
