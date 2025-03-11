package cn.uncleyumo.talkspacestudio.constant;

import lombok.Data;

/**
 * @author uncle_yumo
 * @fileName LlmPromptConstant
 * @createDate 2025/3/10 March
 * @school 无锡学院
 * @studentID 22344131
 * @description 大模型提示词常量
 */

public class LlmPromptConstant {
    /**
     * use: 限制模型的输出为纯文本，不包含markdown语法、代码等；
     */
    public static final String ONLY_PLAIN_TEXT = """
            注意！你所回答的内容不能包含markdown语法、代码等，只能是纯文本格式！
            """;
}
