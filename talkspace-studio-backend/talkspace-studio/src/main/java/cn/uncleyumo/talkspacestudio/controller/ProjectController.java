package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.util.SaResult;
import cn.uncleyumo.talkspacestudio.entity.dto.*;
import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.entity.vo.*;
import cn.uncleyumo.talkspacestudio.service.CommunityCollectionService;
import cn.uncleyumo.talkspacestudio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final CommunityCollectionService communityCollectionService;

    @Autowired
    public ProjectController(ProjectService projectService, CommunityCollectionService communityCollectionService) {
        this.projectService = projectService;
        this.communityCollectionService = communityCollectionService;
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
    public SaResult generatePodcast(@Valid @RequestBody GeneratePodcastDto generatePodcastDto) {

        log.info("AI生成剧集：{}", generatePodcastDto);
        GenerateEpisodesDto generateEpisodesDto = projectService.generateEpisodes(generatePodcastDto);
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
    public SaResult getUserScript(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        log.info("获取用户剧本：{}", projectId);
        UserScriptWithProjectIdAndCharacterNameVo userScript = projectService.getUserScript(Long.parseLong(projectId));
        return SaResult.data(userScript);
    }

    @GetMapping("/get_project_role_list/{projectId}")
    @Operation(summary = "获取项目角色列表")
    public SaResult getProjectRoleList(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
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

    @GetMapping("/get_final_project_list/{projectId}")
    @Operation(summary = "获取最终播客列表")
    public SaResult getFinalProjectList(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        FinalProjectVo finalProject = projectService.getFinalProjectVo(Long.parseLong(projectId));
        return SaResult.data(finalProject);
    }

    @DeleteMapping("delete_project/{projectId}")
    @Operation(summary = "删除项目")
    public SaResult deleteProject(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        log.info("删除项目：{}", projectId);
        projectService.deleteProject(Long.parseLong(projectId));
        return SaResult.ok("已彻底删除指定播客项目");
    }

    @PostMapping("community_works")
    @Operation(summary = "获取社区作品列表")
    public SaResult getCommunityWorks(@RequestBody PublishedProjectDto publishedProjectDto) {
        PageResult<List<PublishedProjectVo>> pageResult = projectService.getCommunityWorks(publishedProjectDto);
        return SaResult.data(pageResult);
    }

    @PostMapping("my_published_works")
    @Operation(summary = "获取我发布的作品列表")
    public SaResult getMyPublishedWorks(@RequestBody PublishedProjectDto publishedProjectDto) {
//        PageResult<List<PublishedProjectVo>> pageResult = projectService.getCommunityWorks(publishedProjectDto);
//        return SaResult.data(pageResult);
        return SaResult.ok("暂未实现：获取我发布的作品列表");
    }

    @PostMapping("my_collection_works")
    @Operation(summary = "获取我收藏的作品列表")
    public SaResult getMyCollectionWorks(@RequestBody PublishedProjectDto publishedProjectDto) {
//        PageResult<List<PublishedProjectVo>> pageResult = projectService.getCommunityWorks(publishedProjectDto);
//        return SaResult.data(pageResult);
        return SaResult.ok("暂未实现：获取我收藏的作品列表");
    }

    @PutMapping("publish_project/{projectId}")
    @Operation(summary = "发布项目")
    public SaResult publishProject(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        log.info("发布项目：{}", projectId);
        projectService.publishProject(Long.parseLong(projectId));
        return SaResult.ok("已发布指定播客项目");
    }

    @PutMapping("cancel_publish_project/{projectId}")
    @Operation(summary = "取消发布项目")
    public SaResult cancelPublishProject(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        log.info("取消发布项目：{}", projectId);
        projectService.cancelPublishProject(Long.parseLong(projectId));
        return SaResult.ok("已取消发布指定播客项目");
    }

    @PutMapping("collect_project/{projectId}")
    @Operation(summary = "收藏项目")
    public SaResult collectProject(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        log.info("收藏项目：{}", projectId);
        communityCollectionService.collectProject(Long.parseLong(projectId));
        return SaResult.ok("已收藏该项目");
    }

    @PutMapping("cancel_collect_project/{projectId}")
    @Operation(summary = "取消收藏项目")
    public SaResult cancelCollectProject(
            @Parameter(description = "项目ID", required = true)
            @PathParam("projectId") @PathVariable String projectId
    ) {
        log.info("收藏项目：{}", projectId);
        communityCollectionService.cancelCollectProject(Long.parseLong(projectId));
        return SaResult.ok("已取消收藏该项目");
    }
}
