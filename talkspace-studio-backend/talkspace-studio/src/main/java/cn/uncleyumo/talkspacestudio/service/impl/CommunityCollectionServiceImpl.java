package cn.uncleyumo.talkspacestudio.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.uncleyumo.talkspacestudio.constant.CommonErrorMessage;
import cn.uncleyumo.talkspacestudio.entity.pojo.CommunityCollection;
import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.enums.ProjectStatusEnum;
import cn.uncleyumo.talkspacestudio.mapper.CommunityCollectionMapper;
import cn.uncleyumo.talkspacestudio.service.CommunityCollectionService;
import cn.uncleyumo.talkspacestudio.service.ProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName CommunityCollectionServiceImpl
 * @createDate 2025/3/25 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Service
@Slf4j
public class CommunityCollectionServiceImpl
        extends ServiceImpl<CommunityCollectionMapper, CommunityCollection>
        implements CommunityCollectionService {

    private final ProjectService projectService;

    @Autowired
    public CommunityCollectionServiceImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public void collectProject(long projectId) {
        log.info("即将收藏的项目ID：{}", projectId);
        Project project = projectService.getById(projectId);
        if (project == null) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() != ProjectStatusEnum.PUBLISHED) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_NOT_PUBLISHED);
        }
        long count = this.count(new QueryWrapper<CommunityCollection>()
                .eq("user_id", StpUtil.getLoginIdAsLong())
                .eq("project_id", projectId));

        if (count > 0) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_ALREADY_COLLECTED);
        }
        CommunityCollection communityCollection = new CommunityCollection();
        communityCollection.setProjectId(projectId);
        communityCollection.setUserId(StpUtil.getLoginIdAsLong());
        this.save(communityCollection);
    }

    @Override
    public void cancelCollectProject(long projectId) {
        log.info("即将取消收藏的项目ID：{}", projectId);
        long count = this.count(new QueryWrapper<CommunityCollection>()
                .eq("user_id", StpUtil.getLoginIdAsLong())
                .eq("project_id", projectId));
        if (count == 0) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_NOT_COLLECTED);
        }
        if (count > 1) {
            log.warn("用户{}收藏的项目中存在多个项目ID为{}的记录，将全部删除", StpUtil.getLoginId(), projectId);
        }
        this.remove(new QueryWrapper<CommunityCollection>()
                .eq("user_id", StpUtil.getLoginIdAsLong())
                .eq("project_id", projectId));
    }
}
