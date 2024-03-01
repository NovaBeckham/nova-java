package com.nova.controller;

import com.nova.annotation.OptLog;
import com.nova.model.vo.ArticleVO;
import com.nova.model.vo.ResultVO;
import com.nova.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.nova.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Api(tags = "文章模块")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("保存和修改文章")
    @PostMapping("/admin/articles")
    public ResultVO<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return ResultVO.ok();
    }

}
