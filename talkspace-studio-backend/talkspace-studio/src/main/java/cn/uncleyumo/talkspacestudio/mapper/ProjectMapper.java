package cn.uncleyumo.talkspacestudio.mapper;

import cn.uncleyumo.talkspacestudio.entity.pojo.Project;
import cn.uncleyumo.talkspacestudio.entity.pojo.UserScript;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author uncle_yumo
 * @fileName ProjectMapper
 * @createDate 2025/3/15 March
 * @school 无锡学院
 * @studentID 22344131
 * @description
 */

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

//    @Select("SELECT user_script FROM project WHERE id = #{projectId}")
//    UserScript getUserScript(Long projectId);
}
