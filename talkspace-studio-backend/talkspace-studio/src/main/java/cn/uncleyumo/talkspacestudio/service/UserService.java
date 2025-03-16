package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.dto.UserLoginDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserRegisterDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserUpdateDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.User;
import jakarta.validation.Valid;

/**
 * @author uncle_yumo
 * @fileName UserService
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public interface UserService {

    User login(UserLoginDto userLoginDto);

    User register(UserRegisterDto userRegisterDto);

    User getLoginUser();

    User update(@Valid UserUpdateDto userRegisterDto);
}
