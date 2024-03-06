package com.nova.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nova.entity.Article;
import com.nova.entity.ArticleTag;
import com.nova.mapper.ArticleMapper;
import com.nova.mapper.ArticleTagMapper;
import com.nova.model.dto.ArticleAdminDTO;
import com.nova.model.dto.ArticleAdminViewDTO;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.vo.ArticleVO;
import com.nova.model.vo.ConditionVO;
import com.nova.service.ArticleService;
import com.nova.service.ArticleTagService;
import com.nova.service.RedisService;
import com.nova.utils.BeanCopyUtils;
import com.nova.utils.PageUtils;
import com.nova.utils.UserUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.nova.constant.RedisConstant.ARTICLE_VIEWS_COUNT;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private RedisService redisService;

    @SneakyThrows
    @Override
    public PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO) {
        CompletableFuture<Integer> asyncCount = CompletableFuture.supplyAsync(() -> articleMapper.countArticleAdmins(conditionVO));
        List<ArticleAdminDTO> articleAdminDTOs = articleMapper.listArticlesAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        articleAdminDTOs.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
        });
        return new PageResultDTO<>(articleAdminDTOs, asyncCount.get(), PageUtils.getCurrent(), PageUtils.getSize());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        Article article = BeanCopyUtils.copyObject(articleVO, Article.class);
        article.setUserId(UserUtils.getUserDetailsDTO().getUserInfoId());
        this.saveOrUpdate(article);
        saveArticleTag(articleVO, article.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ArticleAdminViewDTO getArticleByIdAdmin(Integer articleId) {
        Article article = articleMapper.selectById(articleId);
        List<Integer> tagIds = articleTagMapper.listTagIdsByArticleId(articleId);
        ArticleAdminViewDTO articleAdminViewDTO = BeanCopyUtils.copyObject(article, ArticleAdminViewDTO.class);
        articleAdminViewDTO.setTagIds(tagIds);
        return articleAdminViewDTO;
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
