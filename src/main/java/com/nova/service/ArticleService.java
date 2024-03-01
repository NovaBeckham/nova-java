package com.nova.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nova.entity.Article;
import com.nova.model.vo.ArticleVO;

public interface ArticleService extends IService<Article> {

    void saveOrUpdateArticle(ArticleVO articleVO);

}
