package com.nova.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nova.entity.Tag;
import com.nova.model.dto.TagAdminDTO;
import com.nova.model.dto.TagDTO;
import com.nova.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<TagDTO> listTags();

    List<TagAdminDTO> listTagsAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}
