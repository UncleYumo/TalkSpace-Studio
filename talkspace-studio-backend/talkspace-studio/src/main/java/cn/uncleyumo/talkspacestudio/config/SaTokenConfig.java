package cn.uncleyumo.talkspacestudio.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author uncle_yumo
 * @fileName SaTokenConfig
 * @createDate 2025/3/12 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Configuration
public class SaTokenConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePathUrls = new ArrayList<>();
        excludePathUrls.add("/user/login");
        excludePathUrls.add("/user/register");
        excludePathUrls.add("/user/logout");
        excludePathUrls.add("/ws/**");
        excludePathUrls.add("/**/doc.*");
        excludePathUrls.add("/**/swagger-ui.*");
        excludePathUrls.add("/**/swagger-resources");
        excludePathUrls.add("/**/webjars/**");
        excludePathUrls.add("/**/v3/api-docs/**");

        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathUrls);
    }
}
