package cn.uncleyumo.talkspacestudio.constant;

import cn.uncleyumo.talkspacestudio.entity.dto.AiGenerateUserPromptDto;
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

public class UserPromptLlmPromptConstant {
    public static String getPrompt(AiGenerateUserPromptDto aiGenerateUserPromptDto) {
        return """
                你是一位播客AI提示词书写专家，请根据以下要求生成纯文本格式（且不包含例子中的引号）的指导播客剧本生成的提示词：
                1. 播客的标题，即主要内容为: %s
                2. 你生成的提示词长度应该在20-100个字符左右，要求尽量简单明了，避免过于复杂，但要确保你的提示词能够正确合理地指导后续AI生成的播客剧本的剧情发展。
                3. 你的提示词应该是纯文本内容，不能使用Markdown在内的任何格式。
                4. 你的提示词可以参考下面的例子：
                  4.1 当标题为：《欢乐相声每一天》，你的提示词可以是：“以风趣幽默的方式讲述一段原创相声，要求内容娱乐化、趣味性强，符合大众口味，笑点包袱足够新颖密集，不落俗套。”
                  4.2 当标题为：《英语四级高频词汇讲解》，你的提示词可以是：“以英语四级考试的核心高频词汇为主线，以生动有趣的方式讲解每一个词汇，使得听众能够在短时间内掌握词汇的意思，并能在应用中加深对词汇的理解。”
                  4.3 当标题为：《Java和Golang，谁才是Web的未来？》，你的提示词可以是：”以Java和Golang的特性及优势为主线，介绍它们的应用场景、优势、以及未来的发展方向。避免过多的技术概念，只关注应用层面的问题，让听众能够快速理解并应用到实际工作中。“
                  4.4 当标题为：《如何改善你的睡眠？》，你的提示词可以是：“以睡眠的科学原理为主线，介绍睡眠的原理、作用、以及如何改善睡眠的方法，让听众能够在短时间内掌握睡眠的科学原理，并应用到生活中。”
                  4.5 当标题为：《短视频创作秘籍》，你的提示词可以是：“从算法推荐机制到内容爆点设计，解析短视频创作的底层逻辑与实操技巧”
                  ...
                5. 请注意，你的提示词不宜过于简单，避免出现过多的重复内容，以免造成重复劳动。
                6. 再次提醒，播客的标题为: %s，你的提示词应该是纯文本内容，不能使用Markdown在内的任何格式！
                """.formatted(
                aiGenerateUserPromptDto.getTitle(),
                aiGenerateUserPromptDto.getTitle()
        );
    }
}
