package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.constant.FieldValidatePatternConstant;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author uncle_yumo
 * @fileName UserRegisterDto
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = FieldValidateMessageConstant.USERNAME_NOT_BLANK)
    @Pattern(regexp = FieldValidatePatternConstant.USERNAME_PATTERN, message = FieldValidateMessageConstant.USERNAME_PATTERN_FAIL)
    private String username;

    @NotBlank(message = FieldValidateMessageConstant.PASSWORD_NOT_BLANK)
    @Pattern(regexp = FieldValidatePatternConstant.PASSWORD_PATTERN, message = FieldValidateMessageConstant.PASSWORD_PATTERN_FAIL)
    private String password;

    @NotNull(message = FieldValidateMessageConstant.GENDER_NOT_NULL)
    @Min(value = FieldValidatePatternConstant.GENDER_MALE, message = FieldValidateMessageConstant.GENDER_PATTERN_FAIL)
    @Max(value = FieldValidatePatternConstant.GENDER_FEMALE, message = FieldValidateMessageConstant.GENDER_PATTERN_FAIL)
    private Integer gender;

    private String avatar;
    private LocalDateTime createTime;

}
