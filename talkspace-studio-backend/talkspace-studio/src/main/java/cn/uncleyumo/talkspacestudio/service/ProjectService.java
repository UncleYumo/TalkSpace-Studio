package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.dto.*;
import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.entity.vo.FinalProjectVo;
import cn.uncleyumo.talkspacestudio.entity.vo.ProjectRoleVo;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdAndCharacterNameVo;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdVo;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName ProjectService
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public interface ProjectService {
    long createProject(UserScriptDto userScriptDto);

    void generateEpisodes(GenerateEpisodesDto generateEpisodesDto);

    void aiGenerateUserScript(GenerateAiUserScriptDto generateAiUserScriptDto);

    void startGenerateUserScript(Long projectId);

    List<Project> getProjectList();

    UserScriptWithProjectIdAndCharacterNameVo getUserScript(Long projectId);

    List<ProjectRoleVo> getProjectRolVoList(long l);

    void updateUserScript(UserScriptWithProjectIdAndCharacterNameDto userScriptDto);

    GenerateEpisodesDto generateEpisodes(GeneratePodcastDto generatePodcastDto);

    List<FinalProjectVo> getFinalProjectList(long l);
}
