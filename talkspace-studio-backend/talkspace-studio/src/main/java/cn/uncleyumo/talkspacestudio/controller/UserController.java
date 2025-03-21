package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.uncleyumo.talkspacestudio.entity.dto.UserLoginDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserRegisterDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserUpdateDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.User;
import cn.uncleyumo.talkspacestudio.entity.vo.UserLoginVo;
import cn.uncleyumo.talkspacestudio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author uncle_yumo
 * @fileName UserController
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@RestController
@Slf4j
@Validated
@Tag(name = "用户通用接口")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录接口", description = "用户登录接口")
    public SaResult login(@Valid @RequestBody UserLoginDto userLoginDto) {
        log.info("调用了login接口: {}", userLoginDto);
        User user = userService.login(userLoginDto);
        StpUtil.login(user.getId());
//        Cookie cookie = new Cookie("tssUserId", user.getId().toString());
//        cookie.setMaxAge(60 * 60 * 24 * 30);  // 设置cookie有效期为30天
//        response.addCookie(cookie);
        return SaResult.data(BeanUtil.copyProperties(user, UserLoginVo.class));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口", description = "用户注册接口")
    public SaResult register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        log.info("调用了register接口: {}", userRegisterDto);
        User user = userService.register(userRegisterDto);
        return SaResult.data(BeanUtil.copyProperties(user, UserLoginVo.class));
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前登录用户信息接口", description = "获取当前登录用户信息接口")
    public SaResult info() {
        log.info("调用了info接口");
        User user = userService.getLoginUser();
        return SaResult.data(BeanUtil.copyProperties(user, UserLoginVo.class));
    }

    @GetMapping("/logout")
    @Operation(summary = "用户登出接口", description = "用户登出接口")
    public SaResult logout(HttpServletResponse response) {
        log.info("调用了logout接口");
        StpUtil.logout();
//        Cookie cookie = new Cookie("tssUserId", "");
//        cookie.setMaxAge(0);  // 设置cookie有效期为0
//        response.addCookie(cookie);
        return SaResult.ok();
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户信息接口", description = "更新用户信息接口")
    public SaResult update(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        log.info("调用了update接口: {}", userUpdateDto);
        User user = userService.update(userUpdateDto);
        return SaResult.data(BeanUtil.copyProperties(user, UserLoginVo.class));
    }
}
