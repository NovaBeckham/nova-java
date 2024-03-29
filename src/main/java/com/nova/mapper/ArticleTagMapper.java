package com.nova.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nova.entity.ArticleTag;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    List<Integer> listTagIdsByArticleId(Integer articleId);

}
