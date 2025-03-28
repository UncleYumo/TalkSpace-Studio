package cn.uncleyumo.talkspacestudio.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.uncleyumo.talkspacestudio.constant.AliyunTtsModelConstant;
import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.constant.UserScriptLlmPromptConstant;
import cn.uncleyumo.talkspacestudio.constant.WebSocketMessageTypeConstant;
import cn.uncleyumo.talkspacestudio.entity.dto.*;
import cn.uncleyumo.talkspacestudio.entity.pojo.*;
import cn.uncleyumo.talkspacestudio.entity.vo.*;
import cn.uncleyumo.talkspacestudio.entity.vo.caltokenrequest.FinalEpisodeVo;
import cn.uncleyumo.talkspacestudio.enums.AliyunLlmModelEnum;
import cn.uncleyumo.talkspacestudio.enums.ProjectStatusEnum;
import cn.uncleyumo.talkspacestudio.mapper.ProjectMapper;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import cn.uncleyumo.talkspacestudio.server.WebSocketServer;
import cn.uncleyumo.talkspacestudio.service.*;
import cn.uncleyumo.talkspacestudio.utils.AliyunLlmUtil;
import cn.uncleyumo.talkspacestudio.utils.AliyunTtsUtil;
import cn.uncleyumo.talkspacestudio.utils.MinioUtil;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisAudioFormat;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author uncle_yumo
 * @fileName ProjectServiceImpl
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
@EnableAsync
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    private final ProjectRoleService projectRoleService;
    private final ProjectMapper projectMapper;
    private final AliyunTtsUtil aliyunTtsUtil;
    private final MinioUtil minioUtil;
    private final EpisodeService episodeService;
    private final AliyunTtsProperty aliyunTtsProperty;
    private final AliyunLlmUtil aliyunLlmUtil;
    private final CommunityCollectionService communityCollectionService;
    private final UserService userService;

    @Autowired
    public ProjectServiceImpl(AliyunLlmUtil aliyunLlmUtil, ProjectRoleService projectRoleService,
                              ProjectMapper projectMapper, AliyunTtsUtil aliyunTtsUtil,
                              MinioUtil minioUtil, EpisodeService episodeService,
                              AliyunTtsProperty aliyunTtsProperty, AliyunLlmUtil aliyunLlmUtil1,
                              @Lazy
                              CommunityCollectionService communityCollectionService,
                              UserService userService) {
        this.projectRoleService = projectRoleService;
        this.projectMapper = projectMapper;
        this.aliyunTtsUtil = aliyunTtsUtil;
        this.minioUtil = minioUtil;
        this.episodeService = episodeService;
        this.aliyunTtsProperty = aliyunTtsProperty;
        this.aliyunLlmUtil = aliyunLlmUtil1;
        this.communityCollectionService = communityCollectionService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public long createProject(UserScriptDto userScriptDto) {
        Project project = new Project();
        project.setUserId(StpUtil.getLoginIdAsLong());
        project.setStatus(ProjectStatusEnum.DRAFT);
        BeanUtils.copyProperties(userScriptDto, project);
        save(project);
        if (project.getId() == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_SAVE_ERROR);
        }
        List<ProjectRole> projectRoles = new ArrayList<>();
        userScriptDto.getRoles().forEach(role -> {
            ProjectRole projectRole = new ProjectRole();
            projectRole.setProjectId(project.getId());
            BeanUtils.copyProperties(role, projectRole);
            projectRoles.add(projectRole);
        });
        projectRoleService.saveBatch(projectRoles);
        return project.getId();
    }

    @Override
    @Async
    @Transactional
    public void generateEpisodes(GenerateEpisodesDto generateEpisodesDto) {

        log.info("接收到的generateEpisodesDto: {}", generateEpisodesDto);

        Project p = this.getById(generateEpisodesDto.getProjectId());
        if (p == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }

//        if (p.getUserId() != StpUtil.getLoginIdAsLong()) {
//            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
//        }

        // 删除原有剧本
        QueryWrapper<Episode> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", generateEpisodesDto.getProjectId());
        episodeService.remove(queryWrapper);
        log.info("原有剧本已删除");
        SpeechSynthesisParam param = SpeechSynthesisParam.builder()
                .speechRate(generateEpisodesDto.getSpeechRate())
                .pitchRate(generateEpisodesDto.getPitchRate())
                .volume(generateEpisodesDto.getVolume())
                .format(SpeechSynthesisAudioFormat.MP3_44100HZ_MONO_256KBPS)
                .model(AliyunTtsModelConstant.COSY_VOICE_V1)
                .apiKey(aliyunTtsProperty.getApiKey())
                .build();
        List<Episode> episodes = new ArrayList<>();
        for (int i = 0; i < generateEpisodesDto.getEpisodes().size(); i++) {
            AudioResultVo audioResultVo = aliyunTtsUtil.episodes2Speech(generateEpisodesDto.getEpisodes().get(i).getContent(), param);
            String fileUrl = minioUtil.uploadFile(
                    audioResultVo.getAudioStream(),
                    "tts-store/" + UUID.randomUUID() + ".mp3",
                    "audio/mp3"
            );
            Episode episode = Episode.builder()
                    .projectId(generateEpisodesDto.getProjectId())
                    .subTitle(generateEpisodesDto.getEpisodes().get(i).getSubTitle())
                    .content(generateEpisodesDto.getEpisodes().get(i).getContent())
                    .sequence(i)
                    .duration(audioResultVo.getDuration())
                    .audioUrl(fileUrl)
                    .build();
            episodes.add(episode);
        }
        log.info("准备保存episodes: {}", episodes);
        episodeService.saveBatch(episodes);
        log.info("episodes saved");
        // 更新项目状态
        Project project = projectMapper.selectById(generateEpisodesDto.getProjectId());
        project.setStatus(ProjectStatusEnum.PODCAST);
        projectMapper.updateById(project);
        // 发送 WebSocket 通知
        WebSocketServer.sendMessage(
                generateEpisodesDto.getUserId().toString(),
                WebSocketMessageTypeConstant.AI_PODCAST_GENERATE_COMPLETE + String.format("项目[%s]的播客生成完成", project.getTitle())
        );
    }


    // 原方法：仅负责立即更新状态并返回
    @Transactional
    public void startGenerateUserScript(Long projectId) {
        log.info("接收到的projectId: {}", projectId); // 添加日志
        Project project = this.getById(projectId);
        if (project == null) {
            throw new RuntimeException("项目不存在");
        }
        project.setStatus(ProjectStatusEnum.DRAFT_SCRIPT);
        this.updateById(project); // 立即提交事务
        log.info("项目[{}]状态已更新为[{}]，开始生成剧本", project.getTitle(), project.getStatus());
    }

    @Override
    public List<Project> getProjectList() {
        // 按时间倒序排列
        return this.list(new QueryWrapper<Project>().eq("user_id", StpUtil.getLoginIdAsLong()).orderByDesc("create_time"));
    }

    @Override
    public UserScriptWithProjectIdAndCharacterNameVo getUserScript(Long projectId) {
        try {
            Project project = getById(projectId);
            if (project == null) {
                throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
            }
            log.info("项目: {}", project);

            if (project.getUserId() != StpUtil.getLoginIdAsLong()) {
                throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
            }

            if (project.getStatus() == null || project.getStatus().equals(ProjectStatusEnum.DRAFT)) {
                log.info("项目状态为草稿，无法获取剧本");
                throw new RuntimeException(CommonErrorMessage.USER_SCRIPT_NOT_GENERATED);
            }

            UserScript userScript = project.getUserScript();

            if (userScript == null) {
                throw new RuntimeException(CommonErrorMessage.SCRIPT_NOT_GENERATED);
            }
            log.info("剧本: {}", userScript);

            // 获取当前项目的所有角色，并转换为timbre->角色名的Map
            List<ProjectRole> projectRoles = projectRoleService.list(
                    new QueryWrapper<ProjectRole>().eq("project_id", projectId)
            );
            if (projectRoles.isEmpty()) {
                throw new RuntimeException(CommonErrorMessage.ROLES_NOT_FOUND);
            }
            log.info("角色列表: {}", projectRoles);

            Map<String, String> roleNameMap = projectRoles.stream()
                    .collect(Collectors.toMap(ProjectRole::getTimbre, ProjectRole::getCharacterName, (existing, replacement) -> existing));

            // 构建剧本对象
            UserScriptVo userScriptVo = new UserScriptVo();
            userScriptVo.setTitle(project.getTitle());

            List<EpisodeWithCharacterNameVo> episodesWithCharacterNameList = new ArrayList<>();

            // 处理每个剧本片段
            userScript.getEpisodes().forEach(episode -> {
                EpisodeWithCharacterNameVo episodeVo = new EpisodeWithCharacterNameVo();
                episodeVo.setSubTitle(episode.getSubTitle());

                // 新增内容列表转换
                List<EpisodeContentWithCharacterNameVo> contentList = episode.getContent().stream()
                        .map(content -> {
                            EpisodeContentWithCharacterNameVo contentVo = new EpisodeContentWithCharacterNameVo();
                            BeanUtils.copyProperties(content, contentVo);
                            contentVo.setCharacterName(roleNameMap.get(content.getRole()));
                            return contentVo;
                        }).collect(Collectors.toList());

                episodeVo.setContentWithCharacterName(contentList);
                episodesWithCharacterNameList.add(episodeVo);
            });

            userScriptVo.setEpisodesWithCharacterName(episodesWithCharacterNameList);
            return new UserScriptWithProjectIdAndCharacterNameVo(projectId, userScriptVo);

        } catch (RuntimeException e) {
            log.error("获取剧本失败: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取剧本失败", e);
            throw new RuntimeException(CommonErrorMessage.GENERAL_ERROR, e);
        }
    }

    @Override
    public List<ProjectRoleVo> getProjectRolVoList(long projectId) {
        List<ProjectRole> projectRoles = projectRoleService.list(
                new QueryWrapper<ProjectRole>().eq("project_id", projectId)
        );
        if (projectRoles == null || projectRoles.isEmpty()) {
            throw new RuntimeException("项目角色未找到");
        }
        log.info("角色列表: {}", projectRoles);
        List<ProjectRoleVo> projectRoleVos = new ArrayList<>();
        projectRoles.forEach(role -> {
            projectRoleVos.add(new ProjectRoleVo(role.getTimbre(), role.getCharacterName()));
        });
        return projectRoleVos;
    }

    @Override
    @Transactional
    public void updateUserScript(UserScriptWithProjectIdAndCharacterNameDto userScriptDto) {
        // 1. 获取项目实体
        Project project = this.getById(userScriptDto.getProjectId());
        if (project == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }

        if (project.getUserId() != StpUtil.getLoginIdAsLong()) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
        }

        // 2. 构建新的剧本对象
        UserScript newUserScript = new UserScript();
        newUserScript.setTitle(userScriptDto.getUserScriptWithCharacterName().getTitle());
        List<EpisodeVo> newEpisodes = new ArrayList<>();
        userScriptDto.getUserScriptWithCharacterName().getEpisodesWithCharacterName().forEach(episode -> {
            EpisodeVo episodeVo = new EpisodeVo();
            episodeVo.setSubTitle(episode.getSubTitle());
            List<EpisodeContentVo> episodeContentVos = new ArrayList<>();
            episode.getContentWithCharacterName().forEach(item -> {
                EpisodeContentVo contentVo = new EpisodeContentVo();
                BeanUtils.copyProperties(item, contentVo);
                episodeContentVos.add(contentVo);
            });
            episodeVo.setContent(episodeContentVos);
            newEpisodes.add(episodeVo);
        });
        newUserScript.setEpisodes(newEpisodes);
        log.info("新的剧本: {}", newUserScript);

        // 3. 更新项目剧本
        project.setUserScript(newUserScript);
        this.updateById(project);
        log.info("项目[{}]的剧本更新成功", project.getTitle());
    }

    @Override
    @Transactional
    public GenerateEpisodesDto generateEpisodes(GeneratePodcastDto generatePodcastDto) {
        GenerateEpisodesDto generateEpisodesDto = new GenerateEpisodesDto();
        BeanUtil.copyProperties(generatePodcastDto, generateEpisodesDto);
        Project project = this.getById(generateEpisodesDto.getProjectId());
        if (project == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() == null || project.getStatus().equals(ProjectStatusEnum.DRAFT)) {
            throw new RuntimeException(CommonErrorMessage.USER_SCRIPT_NOT_GENERATED);
        }
        generateEpisodesDto.setEpisodes(project.getUserScript().getEpisodes());
        project.setStatus(ProjectStatusEnum.PODCAST_SCRIPT);
        this.updateById(project);
        return generateEpisodesDto;
    }

    @Override
    public FinalProjectVo getFinalProjectVo(long projectId) {
        Project project = this.getById(projectId);
        if (project == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }

        if (project.getUserId() != StpUtil.getLoginIdAsLong()) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
        }

        // 校验项目状态：
        if (!(project.getStatus() == ProjectStatusEnum.PODCAST || project.getStatus() == ProjectStatusEnum.PUBLISHED)) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_HAVE_PODCAST);
        }
        // 构建返回对象
        FinalProjectVo finalProjectVo = new FinalProjectVo();
        BeanUtils.copyProperties(project, finalProjectVo);
        finalProjectVo.setProjectId(projectId);
        List<FinalEpisodeVo> finalEpisodeVoList = new ArrayList<>();
        List<ProjectRole> projectRoleList = projectRoleService.list(new QueryWrapper<ProjectRole>().eq("project_id", projectId));
        if (projectRoleList == null || projectRoleList.isEmpty()) {
            throw new RuntimeException(CommonErrorMessage.ROLES_NOT_FOUND);
        }
        List<Episode> episodeList = episodeService.list(new QueryWrapper<Episode>().eq("project_id", projectId).orderByAsc("sequence"));
        if (episodeList == null || episodeList.isEmpty()) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_HAVE_PODCAST);
        }
        episodeList.forEach(episode -> {
            FinalEpisodeVo finalEpisodeVo = new FinalEpisodeVo();
            BeanUtils.copyProperties(episode, finalEpisodeVo);

            List<EpisodeContentWithCharacterNameVo> contentList = new ArrayList<>();
            episode.getContent().forEach(content -> {
                EpisodeContentWithCharacterNameVo contentVo = new EpisodeContentWithCharacterNameVo();
                BeanUtil.copyProperties(content, contentVo);
                // 设置CharacterName，从角色列表中查找
                ProjectRole role = projectRoleList.stream()
                       .filter(r -> r.getTimbre().equals(content.getRole()))
                       .findFirst()
                       .orElse(null);
                if (role == null) {
                    throw new RuntimeException(CommonErrorMessage.ROLE_NOT_FOUND);
                }
                contentVo.setCharacterName(role.getCharacterName());
                contentList.add(contentVo);
            });
            finalEpisodeVo.setContent(contentList);
            finalEpisodeVoList.add(finalEpisodeVo);
        });
        finalProjectVo.setEpisodes(finalEpisodeVoList);
        return finalProjectVo;
    }

    @Override
    @Transactional
    public void deleteProject(long projectId) {

        Project project = this.getById(projectId);
        if (project == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }

        if (project.getUserId() != StpUtil.getLoginIdAsLong()) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
        }

        // 1. 删除所有的Episode
        episodeService.remove(new QueryWrapper<Episode>().eq("project_id", projectId));

        // 2. 删除所有的ProjectRole
        projectRoleService.remove(new QueryWrapper<ProjectRole>().eq("project_id", projectId));

        // 3. 删除项目
        this.removeById(projectId);

        // 4. 删除分享和收藏的项目（暂不实现）

        // 5. 发送 WebSocket 通知
        WebSocketServer.sendMessage(
                StpUtil.getLoginIdAsString(),
                WebSocketMessageTypeConstant.PROJECT_DELETE + String.format("项目[%s]已删除", project.getTitle())
        );
    }

    @Override
    @Transactional
    public PageResult<List<PublishedProjectVo>> getCommunityWorks(PublishedProjectDto publishedProjectDto) {

        log.info("查询社区作品参数: {}", publishedProjectDto);

        Page<Project> page = this.page(
                new Page<>(publishedProjectDto.getPageNum(), publishedProjectDto.getPageSize()),
                new QueryWrapper<Project>()
                        .eq("status", ProjectStatusEnum.PUBLISHED)
                        .orderByAsc("create_time"));

        List<Project> records = page.getRecords();

        List<PublishedProjectVo> publishedProjectVoList = new ArrayList<>();

        records.forEach(project -> {
            PublishedProjectVo publishedProjectVo = new PublishedProjectVo();
            BeanUtils.copyProperties(project, publishedProjectVo);

            publishedProjectVo.setUserId(project.getUserId());
            publishedProjectVo.setProjectId(project.getId());
            publishedProjectVo.setPublishedTime(project.getUpdateTime());

            User user = userService.getById(project.getUserId());
            if (user == null) {
                throw new RuntimeException(CommonErrorMessage.USER_NOT_FOUND);
            }
            publishedProjectVo.setAvatar(user.getAvatar());
            publishedProjectVo.setUsername(user.getUsername());
            publishedProjectVo.setGender(user.getGender());

            // 计算收藏数
            publishedProjectVo.setCollectionCount(
                    communityCollectionService.count(new QueryWrapper<CommunityCollection>().eq("project_id", project.getId()))
            );

            // 判断当前用户是否收藏了该项目
            boolean isCollected = false;
            CommunityCollection collection = communityCollectionService.getOne(
                    new QueryWrapper<CommunityCollection>()
                            .eq("user_id", StpUtil.getLoginIdAsLong())
                            .eq("project_id", project.getId())
            );
            if (collection != null) {
                isCollected = true;
            }
            publishedProjectVo.setIsCollected(isCollected);
            publishedProjectVoList.add(publishedProjectVo);
        });

        PageResult<List<PublishedProjectVo>> pageResult = new PageResult<>();
        pageResult.setTotalCount((int) page.getTotal());
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotalPage((int) page.getPages());
        pageResult.setRecords(publishedProjectVoList);
        log.info("查询社区作品结果: {}", pageResult);
        return pageResult;
    }

    @Override
    public void publishProject(long projectId) {
        Project project = this.getById(projectId);

        if (project.getUserId() != StpUtil.getLoginIdAsLong()) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
        }

        if (project == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() == ProjectStatusEnum.PUBLISHED) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_ALREADY_PUBLISHED);
        }
        if (project.getStatus() != ProjectStatusEnum.PODCAST) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_HAVE_PODCAST);
        }
        // 发布项目
        project.setStatus(ProjectStatusEnum.PUBLISHED);
        this.updateById(project);
    }

    @Override
    public void cancelPublishProject(long projectId) {
        // 取消发布项目
        Project project = this.getById(projectId);

        if (project.getUserId() != StpUtil.getLoginIdAsLong()) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
        }

        if (project == null) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() != ProjectStatusEnum.PUBLISHED) {
            throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_PUBLISHED);
        }
        project.setStatus(ProjectStatusEnum.PODCAST);
        this.updateById(project);
    }

    @Override
    @Transactional
    public PageResult<List<PublishedProjectVo>> getMyPublishedWorks(PublishedProjectDto publishedProjectDto) {

        log.info("查询社区作品参数: {}", publishedProjectDto);

        Page<Project> page = this.page(
                new Page<>(publishedProjectDto.getPageNum(), publishedProjectDto.getPageSize()),
                new QueryWrapper<Project>()
                        .eq("status", ProjectStatusEnum.PUBLISHED)
                        .eq("user_id", StpUtil.getLoginIdAsLong())
                        .orderByAsc("create_time"));

        List<Project> records = page.getRecords();

        List<PublishedProjectVo> publishedProjectVoList = new ArrayList<>();

        records.forEach(project -> {
            PublishedProjectVo publishedProjectVo = new PublishedProjectVo();
            BeanUtils.copyProperties(project, publishedProjectVo);

            publishedProjectVo.setUserId(project.getUserId());
            publishedProjectVo.setProjectId(project.getId());
            publishedProjectVo.setPublishedTime(project.getUpdateTime());

            User user = userService.getById(project.getUserId());
            if (user == null) {
                throw new RuntimeException(CommonErrorMessage.USER_NOT_FOUND);
            }
            publishedProjectVo.setAvatar(user.getAvatar());
            publishedProjectVo.setUsername(user.getUsername());
            publishedProjectVo.setGender(user.getGender());

            // 计算收藏数
            publishedProjectVo.setCollectionCount(
                    communityCollectionService.count(new QueryWrapper<CommunityCollection>().eq("project_id", project.getId()))
            );

            // 判断当前用户是否收藏了该项目
            boolean isCollected = false;
            CommunityCollection collection = communityCollectionService.getOne(
                    new QueryWrapper<CommunityCollection>()
                            .eq("user_id", StpUtil.getLoginIdAsLong())
                            .eq("project_id", project.getId())
            );
            if (collection != null) {
                isCollected = true;
            }
            publishedProjectVo.setIsCollected(isCollected);
            publishedProjectVoList.add(publishedProjectVo);
        });

        PageResult<List<PublishedProjectVo>> pageResult = new PageResult<>();
        pageResult.setTotalCount((int) page.getTotal());
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotalPage((int) page.getPages());
        pageResult.setRecords(publishedProjectVoList);
        log.info("查询社区作品结果: {}", pageResult);
        return pageResult;
    }

    @Override
    @Transactional
    public PageResult<List<PublishedProjectVo>> getMyCollectionWorks(PublishedProjectDto publishedProjectDto) {
        log.info("查询我的收藏作品参数: {}", publishedProjectDto);

        Long currentUserId = StpUtil.getLoginIdAsLong(); // 获取当前用户ID

        // 1. 分页查询当前用户的收藏记录，获取收藏的项目ID列表
        Page<CommunityCollection> collectionPage = communityCollectionService.page(
                new Page<>(publishedProjectDto.getPageNum(), publishedProjectDto.getPageSize()),
                new QueryWrapper<CommunityCollection>()
                        .eq("user_id", currentUserId)
                        .orderByDesc("create_time") // 按收藏时间倒序
        );

        List<Long> projectIds = collectionPage.getRecords().stream()
                .map(CommunityCollection::getProjectId)
                .distinct() // 去重防止重复收藏导致重复项目
                .collect(Collectors.toList());

        // 2. 根据项目ID列表查询已发布的项目（确保项目处于发布状态）
        List<Project> projects = projectIds.isEmpty() ? Collections.emptyList() : this.listByIds(projectIds);
        List<Project> filteredProjects = projects.stream()
                .filter(p -> ProjectStatusEnum.PUBLISHED.equals(p.getStatus()))
                .sorted(Comparator.comparing(Project::getCreateTime)) // 保持与收藏时间一致的排序
                .collect(Collectors.toList());

        // 3. 转换为VO并填充数据
        List<PublishedProjectVo> publishedProjectVoList = new ArrayList<>();
        filteredProjects.forEach(project -> {
            PublishedProjectVo vo = new PublishedProjectVo();
            BeanUtils.copyProperties(project, vo);
            vo.setUserId(project.getUserId());
            vo.setProjectId(project.getId());
            vo.setPublishedTime(project.getUpdateTime());

            // 填充用户信息
            User user = userService.getById(project.getUserId());
            if (user == null) {
                throw new RuntimeException(CommonErrorMessage.USER_NOT_FOUND);
            }
            vo.setAvatar(user.getAvatar());
            vo.setUsername(user.getUsername());
            vo.setGender(user.getGender());

            // 填充收藏信息（当前用户必然已收藏）
            vo.setCollectionCount(communityCollectionService.count(
                    new QueryWrapper<CommunityCollection>().eq("project_id", project.getId())
            ));
            vo.setIsCollected(true); // 因为是查询自己的收藏列表

            publishedProjectVoList.add(vo);
        });

        // 4. 组装分页结果（注意：这里使用收藏记录的分页数据）
        PageResult<List<PublishedProjectVo>> pageResult = new PageResult<>();
        pageResult.setTotalCount((int) collectionPage.getTotal());
        pageResult.setPageNum((int) collectionPage.getCurrent());
        pageResult.setPageSize((int) collectionPage.getSize());
        pageResult.setTotalPage((int) collectionPage.getPages());
        pageResult.setRecords(publishedProjectVoList);

        log.info("查询我的收藏作品结果: {}", pageResult);
        return pageResult;
    }

    // 异步方法：负责生成剧本和后续操作
    @Override
    @Async
    @Transactional
    public void aiGenerateUserScript(GenerateAiUserScriptDto generateAiUserScriptDto) {
        try {
            // 重新获取项目（避免线程间数据不一致）
            log.info("接收到的projectId: {}", generateAiUserScriptDto.getProjectId()); // 添加日志
            Project project = this.getById(generateAiUserScriptDto.getProjectId());
            if (project == null || !project.getStatus().equals(ProjectStatusEnum.DRAFT_SCRIPT)) {
                log.info("项目状态不正确，不生成剧本");
                return;
            }

            if (!project.getUserId().toString().equals(generateAiUserScriptDto.getUserId().toString())) {
                throw new RuntimeException(CommonErrorMessage.PROJECT_NOT_YOURS);
            }

            // 构建请求参数
            List<ProjectRole> projectRoles = projectRoleService.list(
                    new QueryWrapper<ProjectRole>().eq("project_id", generateAiUserScriptDto.getProjectId())
            );
            if (projectRoles == null || projectRoles.isEmpty()) {
                throw new RuntimeException("项目角色未找到");
            }

            UserScriptDto userScriptDto = new UserScriptDto();
            BeanUtil.copyProperties(project, userScriptDto);
            userScriptDto.setRoles(projectRoles.stream()
                    .map(role -> {
                        ProjectRoleDto dto = new ProjectRoleDto();
                        BeanUtils.copyProperties(role, dto);
                        return dto;
                    })
                    .collect(Collectors.toList())
            );
            String prompt = UserScriptLlmPromptConstant.getPrompt(userScriptDto);

            // 调用模型生成剧本
            String jsonStr = aliyunLlmUtil.generate(prompt, AliyunLlmModelEnum.QWEN_MAX.getModelName());
            UserScript result = new ObjectMapper().readValue(jsonStr, UserScript.class);

            // 更新数据库
            project.setUserScript(result);
            project.setStatus(ProjectStatusEnum.SCRIPT);
            this.updateById(project);
            log.info("项目[{}]的剧本生成成功", project.getTitle());
            log.info("剧本内容: {}", project.getUserScript());

            // 发送 WebSocket 通知
            WebSocketServer.sendMessage(
                    generateAiUserScriptDto.getUserId().toString(),
                    String.format(
                            WebSocketMessageTypeConstant.AI_SCRIPT_GENERATE_COMPLETE + "项目[%s]的剧本生成成功",
                            project.getTitle()
                    )
            );
            log.info("项目[{}]的剧本生成成功并发送通知", project.getTitle());

        } catch (Exception e) {
            log.error("生成剧本失败", e);
            // 可选：记录失败状态或重试机制
        }
    }
}
