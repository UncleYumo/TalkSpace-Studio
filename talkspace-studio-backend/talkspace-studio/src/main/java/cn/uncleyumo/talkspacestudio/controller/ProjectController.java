package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.util.SaResult;
import cn.uncleyumo.talkspacestudio.entity.dto.GenerateAiUserScriptDto;
import cn.uncleyumo.talkspacestudio.entity.dto.GenerateEpisodesDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserScriptDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserScriptWithProjectIdAndCharacterNameDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.entity.vo.ProjectRoleVo;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdAndCharacterNameVo;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdVo;
import cn.uncleyumo.talkspacestudio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "创建播客项目")
    public SaResult userScript(@Valid @RequestBody UserScriptDto userScriptDto) {
        log.info("创建用户剧本：{}", userScriptDto);
        long projectId = projectService.createProject(userScriptDto);
        return SaResult.get(200, "播客项目创建成功", projectId);
    }

    @PostMapping("/generate_user_script_ai")
    @Operation(summary = "AI生成用户剧本")
    // 路径参数
    public SaResult generateUserScript(@Valid @RequestBody GenerateAiUserScriptDto generateAiUserScriptDto ) {
        log.info("生成用户剧本：{}", generateAiUserScriptDto);
        projectService.startGenerateUserScript(generateAiUserScriptDto.getProjectId());
        projectService.aiGenerateUserScript(generateAiUserScriptDto);
        return SaResult.ok("后台任务开始生成用户剧本，请稍后查看");
    }

    @PostMapping("/generate_episodes")
    @Operation(summary = "生成剧集")
    public SaResult generateEpisodes(@Valid @RequestBody GenerateEpisodesDto generateEpisodesDto) {
        log.info("生成剧集：{}", generateEpisodesDto);
        projectService.generateEpisodes(generateEpisodesDto);
        return SaResult.ok("剧集生成成功");
    }

    @PostMapping("/generate_podcast_ai")
    @Operation(summary = "AI生成播客")
    public SaResult generatePodcast(@Valid @RequestBody GenerateEpisodesDto generateEpisodesDto) {

        log.info("AI生成剧集：{}", generateEpisodesDto);
        projectService.generateEpisodes(generateEpisodesDto);
        return SaResult.ok("后台任务开始生成剧集，请稍后查看");
    }

    @GetMapping("/get_project_list")
    @Operation(summary = "获取项目列表")
    public SaResult getProjectList() {
        log.info("获取项目列表");
        List<Project> projectList = projectService.getProjectList();
        return SaResult.data(projectList);
    }

    @GetMapping("/get_user_script/{projectId}")
    @Operation(summary = "获取用户剧本")
    public SaResult getUserScript(@PathVariable String projectId) {
        log.info("获取用户剧本：{}", projectId);
        UserScriptWithProjectIdAndCharacterNameVo userScript = projectService.getUserScript(Long.parseLong(projectId));
        return SaResult.data(userScript);
    }

    @GetMapping("/get_project_role_list/{projectId}")
    @Operation(summary = "获取项目角色列表")
    public SaResult getProjectRoleList(@PathVariable String projectId) {
        log.info("获取项目角色列表：{}", projectId);
        List<ProjectRoleVo> projectRoleList = projectService.getProjectRolVoList(Long.parseLong(projectId));
        return SaResult.data(projectRoleList);
    }

    @PostMapping("/update_user_script")
    @Operation(summary = "更新用户剧本")
    public SaResult updateUserScript(@Valid @RequestBody UserScriptWithProjectIdAndCharacterNameDto userScriptDto) {
        log.info("更新用户剧本：{}", userScriptDto);
        projectService.updateUserScript(userScriptDto);
        return SaResult.ok("用户剧本已保存");
    }

}
