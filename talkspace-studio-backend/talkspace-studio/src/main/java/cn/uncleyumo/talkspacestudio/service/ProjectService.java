package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.dto.GenerateEpisodesDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserScriptDto;
import cn.uncleyumo.talkspacestudio.entity.vo.UserScriptWithProjectIdVo;

/**
 * @author uncle_yumo
 * @fileName ProjectService
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public interface ProjectService {
    UserScriptWithProjectIdVo generateUserScript(UserScriptDto userScriptDto);

    void generateEpisodes(GenerateEpisodesDto generateEpisodesDto);
}
