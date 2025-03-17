package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.util.SaResult;
import cn.uncleyumo.talkspacestudio.entity.dto.GenerateEpisodesDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserScriptDto;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdVo;
import cn.uncleyumo.talkspacestudio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author uncle_yumo
 * @fileName ProjectController
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@RestController
@Slf4j
@Tag(name = "项目管理控制器接口")
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create_project")
    @Operation(summary = "创建用户剧本")
    public SaResult userScript(@Valid @RequestBody UserScriptDto userScriptDto) {
        log.info("创建用户剧本：{}", userScriptDto);
        projectService.generateUserScript(userScriptDto);
        return SaResult.ok("剧本创建成功");
    }

    @PostMapping("/generate_episodes")
    @Operation(summary = "生成剧集")
    public SaResult generateEpisodes(@Valid @RequestBody GenerateEpisodesDto generateEpisodesDto) {
        log.info("生成剧集：{}", generateEpisodesDto);
        projectService.generateEpisodes(generateEpisodesDto);
        return SaResult.ok("剧集生成成功");
    }
}
