package cn.uncleyumo.talkspacestudio.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author uncle_yumo
 * @fileName WebMvcConfig
 * @createDate 2025/3/13 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

//@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {
//    @Bean
    public Validator validator() {
        log.info("初始化 Hibernate Validator: 开启快速失败模式");
        ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)  // 快速失败模式
                .buildValidatorFactory();
        return factory.getValidator();
    }
}
