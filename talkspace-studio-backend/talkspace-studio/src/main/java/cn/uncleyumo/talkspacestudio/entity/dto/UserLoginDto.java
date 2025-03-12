package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.constant.FieldValidatePatternConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author uncle_yumo
 * @fileName UserLoginDto
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = FieldValidateMessageConstant.USERNAME_NOT_BLANK)
    @Pattern(regexp = FieldValidatePatternConstant.USERNAME_PATTERN, message = FieldValidateMessageConstant.USERNAME_PATTERN_FAIL)
    private String username;

    @NotBlank(message = FieldValidateMessageConstant.PASSWORD_NOT_BLANK)
    @Pattern(regexp = FieldValidatePatternConstant.PASSWORD_PATTERN, message = FieldValidateMessageConstant.PASSWORD_PATTERN_FAIL)
    private String password;
}
