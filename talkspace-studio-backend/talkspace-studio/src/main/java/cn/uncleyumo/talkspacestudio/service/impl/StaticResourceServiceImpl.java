package cn.uncleyumo.talkspacestudio.service.impl;

import cn.hutool.core.lang.UUID;
import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.entity.vo.CalTokenRequestVo;
import cn.uncleyumo.talkspacestudio.properties.AliyunTtsProperty;
import cn.uncleyumo.talkspacestudio.service.StaticResourceService;
import cn.uncleyumo.talkspacestudio.service.TestService;
import cn.uncleyumo.talkspacestudio.utils.LlmTextUtil;
import cn.uncleyumo.talkspacestudio.utils.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author uncle_yumo
 * @fileName TestServiceImpl
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
@Slf4j
public class StaticResourceServiceImpl implements StaticResourceService {

    private final AliyunTtsProperty property;
    private final MinioUtil minioUtil;

    @Autowired
    public StaticResourceServiceImpl(AliyunTtsProperty property, MinioUtil minioUtil) {
        this.property = property;
        this.minioUtil = minioUtil;
    }

    @Override
    public Integer getTtsMaxTokens() {
        return property.getMaxTokens();
    }

    @Override
    public String fileUpload(MultipartFile file) {
        try {
            return minioUtil.uploadFile(
                    file.getInputStream(),
                    UUID.randomUUID().toString(),
                    file.getContentType()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String fileUploadWithPath(MultipartFile file, String path) {
        // 文件上传（带存储路径：path1-path2-path3）
        // 构造存储路径
        String[] paths = path.split("-");
        // 截取file的后缀名
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        StringBuilder pathBuilder = new StringBuilder();
        for (String p : paths) {
            pathBuilder.append(p).append("/");
        }
        log.info("上传文件到路径：{}", pathBuilder);
        try {
            return minioUtil.uploadFile(
                    file.getInputStream(),
                    pathBuilder.append(file.getOriginalFilename()).toString(),
                    file.getContentType()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
