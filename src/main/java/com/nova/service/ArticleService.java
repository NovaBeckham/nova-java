package com.nova.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nova.entity.Article;
import com.nova.model.dto.ArticleAdminDTO;
import com.nova.model.dto.ArticleAdminViewDTO;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.vo.ArticleVO;
import com.nova.model.vo.ConditionVO;

public interface ArticleService extends IService<Article> {

    PageResultDTO<ArticleAdminDTO> listArticlesAdmin(ConditionVO conditionVO);

    void saveOrUpdateArticle(ArticleVO articleVO);

    ArticleAdminViewDTO getArticleByIdAdmin(Integer articleId);

}
