package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.dto.*;
import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.entity.vo.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName ProjectService
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public interface ProjectService extends IService<Project> {
    long createProject(UserScriptDto userScriptDto);

    void generateEpisodes(GenerateEpisodesDto generateEpisodesDto);

    void aiGenerateUserScript(GenerateAiUserScriptDto generateAiUserScriptDto);

    void startGenerateUserScript(Long projectId);

    List<Project> getProjectList();

    UserScriptWithProjectIdAndCharacterNameVo getUserScript(Long projectId);

    List<ProjectRoleVo> getProjectRolVoList(long l);

    void updateUserScript(UserScriptWithProjectIdAndCharacterNameDto userScriptDto);

    GenerateEpisodesDto generateEpisodes(GeneratePodcastDto generatePodcastDto);

    FinalProjectVo getFinalProjectVo(long projectId);

    void deleteProject(long projectId);

    PageResult<List<PublishedProjectVo>> getCommunityWorks(PublishedProjectDto publishedProjectDto);

    void publishProject(long projectId);

    void cancelPublishProject(long projectId);

    PageResult<List<PublishedProjectVo>> getMyPublishedWorks(PublishedProjectDto publishedProjectDto);

    PageResult<List<PublishedProjectVo>> getMyCollectionWorks(PublishedProjectDto publishedProjectDto);
}
