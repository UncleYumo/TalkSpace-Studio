package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.temp.CountTokenDto;
import cn.uncleyumo.talkspacestudio.entity.vo.CalTokenRequestVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author uncle_yumo
 * @fileName StaticResourceService.java
 * @createDate 2025/3/11 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

public interface StaticResourceService {

    Integer getTtsMaxTokens();

    String fileUpload(MultipartFile file);

    String fileUploadWithPath(MultipartFile file, String path);
}
