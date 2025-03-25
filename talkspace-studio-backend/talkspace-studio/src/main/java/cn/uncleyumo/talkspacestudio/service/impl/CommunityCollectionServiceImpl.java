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
        Project project = projectService.getById(projectId);
        if (project == null) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_NOT_FOUND);
        }
        if (project.getStatus() != ProjectStatusEnum.PUBLISHED) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_NOT_PUBLISHED);
        }
        List<CommunityCollection> userId = this.list(new QueryWrapper<CommunityCollection>().eq("user_id", StpUtil.getLoginId()));
        if (!userId.isEmpty()) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_ALREADY_COLLECTED);
        }
        CommunityCollection communityCollection = new CommunityCollection();
        communityCollection.setProjectId(projectId);
        communityCollection.setUserId(StpUtil.getLoginIdAsLong());
        this.save(communityCollection);
    }

    @Override
    public void cancelCollectProject(long projectId) {
        CommunityCollection communityCollection = this.getOne(new QueryWrapper<CommunityCollection>()
               .eq("user_id", StpUtil.getLoginIdAsLong())
               .eq("project_id", projectId));
        if (communityCollection == null) {
            throw new IllegalArgumentException(CommonErrorMessage.PROJECT_NOT_COLLECTED);
        }
        this.removeById(communityCollection.getId());
    }
}
