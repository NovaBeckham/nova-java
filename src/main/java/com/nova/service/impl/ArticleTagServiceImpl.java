package com.nova.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nova.entity.ArticleTag;
import com.nova.mapper.ArticleTagMapper;
import com.nova.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
