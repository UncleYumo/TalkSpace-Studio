package cn.uncleyumo.talkspacestudio.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.uncleyumo.talkspacestudio.constant.AvatarFileUrlConstant;
import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.entity.dto.UserLoginDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserRegisterDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserUpdateDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.User;
import cn.uncleyumo.talkspacestudio.mapper.UserMapper;
import cn.uncleyumo.talkspacestudio.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author uncle_yumo
 * @fileName UserServiceImpl
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException(CommonErrorMessage.PASSWORD_ERROR);
        }
        return user;
    }

    @Override
    public User register(UserRegisterDto userRegisterDto) {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().lambda()
                        .eq(User::getUsername, userRegisterDto.getUsername())
        );
        if (user != null) {
            throw new IllegalArgumentException(CommonErrorMessage.USER_ALREADY_EXIST);
        }
        User newUser = BeanUtil.copyProperties(userRegisterDto, User.class);
        newUser.setAvatar(AvatarFileUrlConstant.getOneAvatarUrl(userRegisterDto.getGender()));
        newUser.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        userMapper.insert(newUser);
        StpUtil.login(newUser.getId());
        return newUser;
    }

    @Override
    public User getLoginUser() {
        Long userId = StpUtil.getLoginIdAsLong();
        return userMapper.selectById(userId);
    }

    @Override
    public User update(UserUpdateDto userUpdateDto) {
        User user = userMapper.selectOne( new QueryWrapper<User>().lambda()
                .eq(User::getUsername, userUpdateDto.getUsername()));
        if (user != null) {
            throw new IllegalArgumentException(CommonErrorMessage.USER_ALREADY_EXIST);
        }
        User newUser = BeanUtil.copyProperties(userUpdateDto, User.class);
        if (userUpdateDto.getPassword() != null) {
            newUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        newUser.setId(StpUtil.getLoginIdAsLong());
        userMapper.updateById(newUser);
        return getLoginUser();
    }
}
