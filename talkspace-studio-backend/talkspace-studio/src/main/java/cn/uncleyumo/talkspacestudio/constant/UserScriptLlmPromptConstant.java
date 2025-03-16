package cn.uncleyumo.talkspacestudio.constant;

import cn.uncleyumo.talkspacestudio.entity.dto.ProjectRoleDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserScriptDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.TtsTimbre;
import cn.uncleyumo.talkspacestudio.enums.AliyunTtsTimbreEnum;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName UserScriptLlmPromptConstant
 * @createDate 2025/3/10 March
 * @school 无锡学院
 * @studentID 22344131
 * @description 大模型提示词常量
 */

public class UserScriptLlmPromptConstant {

    public static String getPrompt(UserScriptDto userScriptDto) {
        StringBuilder prompt = new StringBuilder("""
            你是一位播客剧本专家，需根据以下要求生成JSON格式的剧本：
            1. 标题：%s，语言：%s，共%d集，每集约%d分钟。
            2. 角色音色信息：
            """.formatted(
                userScriptDto.getTitle(),
                userScriptDto.getLanguage(),
                userScriptDto.getEpisodeCount(),
                userScriptDto.getSingleDuration()
        ));

        // 动态生成角色描述
        List<ProjectRoleDto> roles = userScriptDto.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            TtsTimbre timbre = AliyunTtsTimbreEnum.getTimbreByName(roles.get(i).getTimbre());
            prompt.append("%d. %s（%s）：%s\n".formatted(
                    i + 1,
                    roles.get(i).getCharacterName(),
                    timbre.getTimbreName(),
                    timbre.getDescription()
            ));
        }

        // 强化内容要求
        prompt.append("""
            
            3. 内容要求：
               - 主题：%s
               - 风格：专业严谨且风趣幽默，避免术语堆砌
               - 每集字数：约%d字（适配%d分钟时长）
               - 如果只有一个角色，则对应角色所说的内容相应缩短或延长
               - 如果有多个角色，则角色间的对话尽量平衡，避免出现角色过多或过少的情况，建议每集包含10-15个对话片段，以确保内容充实但不过于冗长。"
            """.formatted(
                userScriptDto.getUserPrompt(),
                200 * userScriptDto.getSingleDuration(),
                userScriptDto.getSingleDuration()
        ));

        prompt.append(USER_SCRIPT_TEMPLATE_EXAMPLE);
        return prompt.toString();
    }

    public static final String USER_SCRIPT_TEMPLATE_EXAMPLE = """
            
            以下是播客剧本的模板：
            {
              "title": "播客标题",
              "episodes": [
                {
                  "subTitle": "第一集：第一集主题介绍",
                  "content": [
                    {
                      "role": "longcheng",
                      "text": "第一集第一句话..."
                    },
                    {
                      "role": "longwan",
                      "text": "第一集第二句话..."
                    }...(如果有的话)
                  ]
                },
                {
                  "subTitle": "第二集：第二集主题介绍",
                  "content": [
                    {
                      "role": "longcheng",
                      "text": "第二集第一句话..."
                    },
                    {
                      "role": "longwan",
                      "text": "第二集第二句话..."
                    }...(如果有的话)
                  ]
                },
                {
                  "subTitle": "第三集：第三集主题介绍",
                  "content": [
                    {
                      "role": "longwan",
                      "text": "第三集第一句话..."
                    },
                    {
                      "role": "longcheng",
                      "text": "第三集第二句话..."
                    }...(如果有的话)
                  ]
                }
              ]
            }
            """;
}
