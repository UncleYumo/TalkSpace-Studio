package cn.uncleyumo.talkspacestudio.constant;

/**
 * @author uncle_yumo
 * @fileName FieldValidateMessageConstant.java
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public class FieldValidateMessageConstant {
    public static final String USERNAME_PATTERN_FAIL = "用户名长度必须为8-20";
    public static final String PASSWORD_PATTERN_FAIL = "密码长度必须为8-20";
    public static final String GENDER_PATTERN_FAIL = "性别参数只能为0或1";
    public static final String USERNAME_NOT_BLANK = "用户名不能为空";
    public static final String PASSWORD_NOT_BLANK = "密码不能为空";
    public static final String GENDER_NOT_NULL = "性别不能为空";
    public static final String LOCAL_DATE_TIME_PATTERN_FAIL = "日期时间格式必须为：yyyy-MM-dd'T'HH:mm:ss";
    public static final String USER_PROMPT_NOT_BLANK = "用户提示词不能为空";
    public static final String TITLE_NOT_BLANK = "播客项目标题不能为空";
    public static final String LANGUAGE_NOT_NULL = "语言风格不能为空";
    public static final String EPISODE_COUNT_NOT_NULL = "播客集数不能为空";
    public static final String EPISODES_COUNT_MIN_VALUE = "播客集数最小值为1";
    public static final String ROLES_NOT_NULL = "音色标识不能为空";
    public static final String SINGLE_DURATION_NOT_NULL = "单集时长不能为空";
    public static final String SINGLE_DURATION_MIN_VALUE = "单集时长最小值为1";
    public static final String SINGLE_DURATION_MAX_VALUE = "单集时长最大值为8";
    public static final String TEXT_NOT_BLANK = "文本内容不能为空";
    public static final String PROJECT_ID_NOT_NULL = "项目ID不能为空";
    public static final String EPISODE_NOT_NULL = "播客集不能为空";
    public static final String EPISODES_COUNT_MAX_VALUE = "播客集数最大值为10";
    public static final String SINGLE_RATE_NOT_NULL = "合成音频的语速不能为空（0.5-2.0）";
    public static final String SINGLE_RATE_MIN_VALUE = "合成音频的语速最小值为0.5";
    public static final String SINGLE_RATE_MAX_VALUE = "合成音频的语速最大值为2.0";
    public static final String SINGLE_PITCH_NOT_NULL = "合成音频的语调不能为空（0.5-2.0）";
    public static final String SINGLE_PITCH_MIN_VALUE = "合成音频的语调最小值为0.5";
    public static final String SINGLE_PITCH_MAX_VALUE = "合成音频的语调最大值为2.0";
    public static final String SINGLE_VOLUME_NOT_NULL = "合成音频的音量不能为空（0-100）";
    public static final String SINGLE_VOLUME_MIN_VALUE = "合成音频的音量最小值为0";
    public static final String SINGLE_VOLUME_MAX_VALUE = "合成音频的音量最大值为100";

}
