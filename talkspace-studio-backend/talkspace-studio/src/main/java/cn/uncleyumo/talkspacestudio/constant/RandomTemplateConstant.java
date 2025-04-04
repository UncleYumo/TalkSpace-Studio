package cn.uncleyumo.talkspacestudio.constant;

import cn.uncleyumo.talkspacestudio.entity.vo.GetRandomTemplateVo;
import cn.uncleyumo.talkspacestudio.entity.vo.RandomTemplateListJsonVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

/**
 * @author uncle_yumo
 * @fileName RandomTemplateConstant
 * @createDate 2025/4/4 April
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

/**
 * @author uncle_yumo
 * @description 随机播客模板常量，包含title和userPrompt，即播客标题和AI生成播客的内容提示词
 */
@Slf4j
@Component
public class RandomTemplateConstant {

    private static Resource templateResource;

    // 通过Setter方法注入资源
    @Value("classpath:static/json/random_template.json")
    public void setTemplateResource(Resource resource) {
        templateResource = resource;
    }

    // 增加初始化方法
    @PostConstruct
    private void init() {
        try {
            // 验证资源是否存在
            if (!templateResource.exists()) {
                log.error("随机模板文件不存在: {}", templateResource.getURI());
            }
        } catch (IOException e) {
            log.error("模板文件加载失败", e);
        }
    }

    public static GetRandomTemplateVo getRandomTemplate() {
        try (InputStream inputStream = templateResource.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            RandomTemplateListJsonVo randomTemplateListJsonVo = mapper.readValue(inputStream, RandomTemplateListJsonVo.class);
            Random random = new Random();
            return randomTemplateListJsonVo.getRandomTemplateList().get(random.nextInt(randomTemplateListJsonVo.getRandomTemplateList().size()));
        } catch (Exception e) {
            log.error("随机播客模板获取失败！", e);
            return GetRandomTemplateVo.builder().title("随机标题生成失败！").userPrompt("随机内容提示词生成失败！").build();
        }
    }
}