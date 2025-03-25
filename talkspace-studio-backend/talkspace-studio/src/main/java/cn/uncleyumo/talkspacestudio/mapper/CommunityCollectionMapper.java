package cn.uncleyumo.talkspacestudio.mapper;

import cn.uncleyumo.talkspacestudio.entity.pojo.CommunityCollection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author uncle_yumo
 * @fileName CommunityCollectionMapper
 * @createDate 2025/3/25 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Mapper
public interface CommunityCollectionMapper extends BaseMapper<CommunityCollection> {
    List<Long> getCollectionCountsByProjectIds(@Param ("projectIds") List<Long> projectIds);
}
