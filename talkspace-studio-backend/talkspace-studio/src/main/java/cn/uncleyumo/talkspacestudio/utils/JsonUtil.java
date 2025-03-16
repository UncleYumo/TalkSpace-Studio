package cn.uncleyumo.talkspacestudio.utils;

/**
 * @author uncle_yumo
 * @fileName JsonUtil
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public class JsonUtil {
    public static String getFirstJsonBlock(String content) {
        // 提取第一个{}块（支持嵌套）
        int startIdx = content.indexOf('{');
        if (startIdx == -1) {
            return "";  // 没有左括号返回空
        }

        int bracketCount = 1;
        int endIdx = -1;
        for (int i = startIdx + 1; i < content.length(); i++) {
            if (content.charAt(i) == '{') {
                bracketCount++;
            } else if (content.charAt(i) == '}') {
                bracketCount--;
            }

            if (bracketCount == 0) {
                endIdx = i;
                break;
            }
        }

        return endIdx != -1 ?
                content.substring(startIdx, endIdx + 1) :  // 完整块
                content.substring(startIdx);               // 不完整块
    }
}
