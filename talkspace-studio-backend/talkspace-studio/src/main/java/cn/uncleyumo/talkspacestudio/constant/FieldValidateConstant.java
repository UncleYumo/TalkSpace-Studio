package cn.uncleyumo.talkspacestudio.constant;

import lombok.Data;

/**
 * @author uncle_yumo
 * @fileName FieldValidateConstant
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public class FieldValidateConstant {
    // 长度为8-20
    public static final String USERNAME_PATTERN = ".{8,20}";
    // 长度为8-20
    public static final String PASSWORD_PATTERN = ".{8,20}";
    // 只能是数字，0或者1
    public static final String GENERAL_PATTERN = "[01]";
}
