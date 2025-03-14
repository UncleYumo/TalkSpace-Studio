package cn.uncleyumo.talkspacestudio.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.uncleyumo.talkspacestudio.entity.dto.UserLoginDto;
import cn.uncleyumo.talkspacestudio.entity.dto.UserRegisterDto;
import cn.uncleyumo.talkspacestudio.entity.pojo.User;
import cn.uncleyumo.talkspacestudio.entity.vo.UserLoginVo;
import cn.uncleyumo.talkspacestudio.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        return SaResult.data(BeanUtil.copyProperties(user, UserLoginVo.class));
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口", description = "用户注册接口")
    public SaResult register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        log.info("调用了register接口: {}", userRegisterDto);
//        userService.register(userRegisterDto);
        return SaResult.ok();
    }

    @GetMapping("/logout")
    @Operation(summary = "用户登出接口", description = "用户登出接口")
    public SaResult logout() {
        log.info("调用了logout接口");
        StpUtil.logout();
        return SaResult.ok();
    }
}
