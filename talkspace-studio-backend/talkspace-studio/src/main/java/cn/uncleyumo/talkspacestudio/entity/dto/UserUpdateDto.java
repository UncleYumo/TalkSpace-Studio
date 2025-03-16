package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import cn.uncleyumo.talkspacestudio.constant.FieldValidatePatternConstant;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author uncle_yumo
 * @fileName UserUpdateDto
 * @createDate 2025/3/14 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonInclude(JsonInclude.Include.NON_NULL)  // 序列化时，null值不序列化
public class UserUpdateDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Pattern(regexp = FieldValidatePatternConstant.USERNAME_PATTERN, message = FieldValidateMessageConstant.USERNAME_PATTERN_FAIL)
    private String username;

    @Pattern(regexp = FieldValidatePatternConstant.PASSWORD_PATTERN, message = FieldValidateMessageConstant.PASSWORD_PATTERN_FAIL)
    private String password;

    @Min(value = FieldValidatePatternConstant.GENDER_MALE, message = FieldValidateMessageConstant.GENDER_PATTERN_FAIL)
    @Max(value = FieldValidatePatternConstant.GENDER_FEMALE, message = FieldValidateMessageConstant.GENDER_PATTERN_FAIL)
    private Integer gender;

    private String avatar;
}
