package cn.uncleyumo.talkspacestudio.service.impl;

import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.entity.dto.UserLoginDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.User;
import cn.uncleyumo.talkspacestudio.mapper.UserMapper;
import cn.uncleyumo.talkspacestudio.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uncle_yumo
 * @fileName UserServiceImpl
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(UserLoginDto userLoginDto) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().lambda()
                        .eq(User::getUsername, userLoginDto.getUsername())
        );
        if (user == null) {
            throw new IllegalArgumentException(CommonErrorMessage.USER_NOT_EXIST);
        }
        if (!user.getPassword().equals(userLoginDto.getPassword())) {
            throw new IllegalArgumentException(CommonErrorMessage.PASSWORD_ERROR);
        }
        return user;
    }
}
