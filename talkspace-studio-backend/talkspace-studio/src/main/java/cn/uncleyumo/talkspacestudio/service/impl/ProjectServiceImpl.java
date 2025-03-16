package cn.uncleyumo.talkspacestudio.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.uncleyumo.talkspacestudio.constant.AliyunTtsModelConstant;
import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.constant.UserScriptLlmPromptConstant;
import cn.uncleyumo.talkspacestudio.entity.dto.GenerateEpisodesDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserScriptDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.Episode;
import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.entity.pojo.ProjectRole;
import cn.uncleyumo.talkspacestudio.entity.vo.AudioResultVo;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptVo;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdVo;
import cn.uncleyumo.talkspacestudio.enums.AliyunLlmModelEnum;
import cn.uncleyumo.talkspacestudio.enums.ProjectStatusEnum;
import cn.uncleyumo.talkspacestudio.mapper.ProjectMapper;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import cn.uncleyumo.talkspacestudio.service.EpisodeService;
import cn.uncleyumo.talkspacestudio.service.ProjectRoleService;
import cn.uncleyumo.talkspacestudio.service.ProjectService;
import cn.uncleyumo.talkspacestudio.utils.AliyunLlmUtil;
import cn.uncleyumo.talkspacestudio.utils.AliyunTtsUtil;
import cn.uncleyumo.talkspacestudio.utils.MinioUtil;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisAudioFormat;
import com.alibaba.dashscope.audio.ttsv2.SpeechSynthesisParam;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author uncle_yumo
 * @fileName ProjectServiceImpl
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    private final AliyunLlmUtil aliyunLlmUtil;
    private final ProjectRoleService projectRoleService;
    private final AliyunTtsUtil aliyunTtsUtil;
    private final MinioUtil minioUtil;
    private final EpisodeService episodeService;
    private final AliyunTtsProperty aliyunTtsProperty;

    @Autowired
    public ProjectServiceImpl(AliyunLlmUtil aliyunLlmUtil, ProjectRoleService projectRoleService, AliyunTtsUtil aliyunTtsUtil, MinioUtil minioUtil, EpisodeService episodeService, AliyunTtsProperty aliyunTtsProperty) {
        this.aliyunLlmUtil = aliyunLlmUtil;
        this.projectRoleService = projectRoleService;
        this.aliyunTtsUtil = aliyunTtsUtil;
        this.minioUtil = minioUtil;
        this.episodeService = episodeService;
        this.aliyunTtsProperty = aliyunTtsProperty;
    }

    @Override
    @Transactional
    public UserScriptWithProjectIdVo generateUserScript(UserScriptDto userScriptDto) {
        String prompt = UserScriptLlmPromptConstant.getPrompt(userScriptDto);
        String jsonStr = aliyunLlmUtil.generate(prompt, AliyunLlmModelEnum.QWEN_MAX.getModelName());
        ObjectMapper objectMapper = new ObjectMapper();
        UserScriptVo result = null;
        try {
            result = objectMapper.readValue(jsonStr, UserScriptVo.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
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
        return new UserScriptWithProjectIdVo(project.getId(), result);
    }

    @Override
    @Transactional
    public void generateEpisodes(GenerateEpisodesDto generateEpisodesDto) {
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
    }
}
