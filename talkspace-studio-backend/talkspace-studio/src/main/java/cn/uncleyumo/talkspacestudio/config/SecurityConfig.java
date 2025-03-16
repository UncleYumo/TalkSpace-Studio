package cn.uncleyumo.talkspacestudio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author uncle_yumo
 * @fileName SecurityConfig
 * @createDate 2025/3/13 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密算法
        // 10代表加密强度，值越大越安全
        return new BCryptPasswordEncoder(10);
    }
}
