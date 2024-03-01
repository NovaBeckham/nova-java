package com.nova.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nova.entity.Article;
import com.nova.entity.ArticleTag;
import com.nova.entity.Tag;
import com.nova.mapper.ArticleMapper;
import com.nova.mapper.ArticleTagMapper;
import com.nova.model.vo.ArticleVO;
import com.nova.service.ArticleService;
import com.nova.service.ArticleTagService;
import com.nova.service.TagService;
import com.nova.utils.BeanCopyUtils;
import com.nova.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private ArticleTagService articleTagService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        Article article = BeanCopyUtils.copyObject(articleVO, Article.class);
        article.setUserId(UserUtils.getUserDetailsDTO().getUserInfoId());
        this.saveOrUpdate(article);
        saveArticleTag(articleVO, article.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveArticleTag(ArticleVO articleVO, Integer articleId) {
        if (Objects.nonNull(articleVO.getId())) {
            articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>().eq(ArticleTag::getArticleId, articleVO.getId()));
        }
        List<Integer> tagIds = articleVO.getTagIds();
        if (CollectionUtils.isNotEmpty(tagIds)) {
            List<ArticleTag> articleTags = tagIds.stream().map(item -> ArticleTag.builder().articleId(articleId).tagId(item).build()).collect(Collectors.toList());
            articleTagService.saveBatch(articleTags);
        }
    }
}
