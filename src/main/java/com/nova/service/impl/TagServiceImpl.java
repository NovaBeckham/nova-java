package com.nova.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nova.entity.ArticleTag;
import com.nova.entity.Tag;
import com.nova.exception.BizException;
import com.nova.mapper.ArticleTagMapper;
import com.nova.mapper.TagMapper;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.dto.TagAdminDTO;
import com.nova.model.dto.TagDTO;
import com.nova.model.vo.ConditionVO;
import com.nova.model.vo.TagVO;
import com.nova.service.TagService;
import com.nova.utils.BeanCopyUtils;
import com.nova.utils.PageUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<TagDTO> listTags() {
        return tagMapper.listTags();
    }

    @SneakyThrows
    @Override
    public PageResultDTO<TagAdminDTO> listTagsAdmin(ConditionVO conditionVO) {
        Integer total = tagMapper.selectCount(new LambdaQueryWrapper<Tag>().like(StringUtils.isNotBlank(conditionVO.getKeywords()), Tag::getTagName, conditionVO.getKeywords()));
        if (total == 0) {
            return new PageResultDTO<>();
        }
        List<TagAdminDTO> tags = tagMapper.listTagsAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        return new PageResultDTO<>(tags, total, PageUtils.getCurrent(), PageUtils.getSize());
    }

    @Override
    public void saveOrUpdateTag(TagVO tagVO) {
        Tag existTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>().select(Tag::getId).eq(Tag::getTagName, tagVO.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVO.getId())) {
            throw new BizException("标签名已存在");
        }
        Tag tag = BeanCopyUtils.copyObject(tagVO, Tag.class);
        this.saveOrUpdate(tag);
    }

    @Override
    public void deleteTag(List<Integer> tagIds) {
        Integer count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>().in(ArticleTag::getTagId, tagIds));
        if (count > 0) {
            throw new BizException("删除失败，该标签下存在文章");
        }
        tagMapper.deleteBatchIds(tagIds);
    }

}
