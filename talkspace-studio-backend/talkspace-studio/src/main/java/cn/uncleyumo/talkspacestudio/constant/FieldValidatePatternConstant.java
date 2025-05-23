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

public class FieldValidatePatternConstant {
    // 长度为8-20
    public static final String USERNAME_PATTERN = ".{8,20}";
    // 长度为8-20
    public static final String PASSWORD_PATTERN = ".{8,20}";
    public static final int GENDER_MALE = 0;
    public static final int GENDER_FEMALE = 1;
    public static final String LOCAL_DATE_TIME_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$";
}
