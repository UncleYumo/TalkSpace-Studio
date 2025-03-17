package cn.uncleyumo.talkspacestudio.config;

import cn.uncleyumo.talkspacestudio.utils.YumoColorPrintUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author uncle_yumo
 * @fileName WebMvcConfig
 * @createDate 2025/3/13 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
//    @Bean
//    public Validator validator() {
//        log.info("初始化 Hibernate Validator: 开启快速失败模式");
//        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                .failFast(true)  // 快速失败模式
//                .buildValidatorFactory();
//        return factory.getValidator();
//    }

    @Bean
    public YumoColorPrintUtil yumoColorPrintUtil() {
        log.info("初始化 YumoColorPrintUtil");
        return new YumoColorPrintUtil();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 允许所有请求路径跨域访问
                .allowCredentials(true) // 是否携带Cookie，默认false
                .allowedHeaders("*") // 允许的请求头类型
                .maxAge(3600)  // 预检请求的缓存时间（单位：秒）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的请求方法类型
                .allowedOrigins("http://127.0.0.1:5173", "http://localhost:5173"); // 允许哪些域名进行跨域访问
    }
}
