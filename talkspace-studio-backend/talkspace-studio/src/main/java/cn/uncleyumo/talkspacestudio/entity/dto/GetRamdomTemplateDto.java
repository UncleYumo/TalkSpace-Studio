package cn.uncleyumo.talkspacestudio.entity.dto;

import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.constant.FieldValidateMessageConstant;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author uncle_yumo
 * @fileName GetRamdomTemplateDto
 * @createDate 2025/4/4 April
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRamdomTemplateDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = FieldValidateMessageConstant.TITLE_NOT_BLANK)
    private String title;
}
