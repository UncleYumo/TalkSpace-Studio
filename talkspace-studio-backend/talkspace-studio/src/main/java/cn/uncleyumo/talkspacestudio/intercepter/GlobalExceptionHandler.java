package cn.uncleyumo.talkspacestudio.intercepter;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.exceptions.ExceptionUtil;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author uncle_yumo
 * @fileName GlobalExceptionHandler
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description 全局异常处理类
 */

@Slf4j
@Hidden  // 隐藏此接口，不在 Swagger 文档中显示
@RestControllerAdvice  // 表示这是一个全局异常处理类
public class GlobalExceptionHandler {

    // 全局异常拦截（拦截项目中的NotLoginException异常）
    @ExceptionHandler(NotLoginException.class)
    public SaResult handlerNotLoginException(NotLoginException nle) throws Exception {
        log.error("!!!NotLoginException异常拦截");
        log.error(ExceptionUtil.stacktraceToString(nle));
        // 判断场景值，定制化异常信息
        String message = "";
        if(nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未能读取到有效 token";
        }
        else if(nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token 无效";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token 已过期";
        }
        else if(nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token 已被顶下线";
        }
        else if(nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token 已被踢下线";
        }
        else if(nle.getType().equals(NotLoginException.TOKEN_FREEZE)) {
            message = "token 已被冻结";
        }
        else if(nle.getType().equals(NotLoginException.NO_PREFIX)) {
            message = "未按照指定前缀提交 token";
        }
        else {
            message = "当前会话未登录";
        }

        // 返回给前端
        return SaResult.error(message);
    }

    @ExceptionHandler(Exception.class)
    public SaResult handlerException(Exception e) {
        log.error("!!!全局异常拦截");
        String stacktrace = ExceptionUtil.stacktraceToString(e);
        if (stacktrace.length() > 200) {
            return SaResult.error("Unknown error, please contact the administrator!");
        }
        log.error(stacktrace);
        return SaResult.error(stacktrace);
    }
}
