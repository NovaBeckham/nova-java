package com.nova.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nova.entity.Tag;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.dto.TagAdminDTO;
import com.nova.model.dto.TagDTO;
import com.nova.model.vo.ConditionVO;
import com.nova.model.vo.TagVO;

import java.util.List;

public interface TagService extends IService<Tag> {

    List<TagDTO> listTags();

    PageResultDTO<TagAdminDTO> listTagsAdmin(ConditionVO conditionVO);

    void saveOrUpdateTag(TagVO tagVO);

    void deleteTag(List<Integer> tagIds);

}
