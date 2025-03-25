package cn.uncleyumo.talkspacestudio.service;

import cn.uncleyumo.talkspacestudio.entity.pojo.CommunityCollection;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
 * @author uncle_yumo
 * @fileName CommunityCollectionService
 * @createDate 2025/3/25 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */


public interface CommunityCollectionService extends IService<CommunityCollection> {

    void collectProject(long projectId);

    void cancelCollectProject(long l);
}
