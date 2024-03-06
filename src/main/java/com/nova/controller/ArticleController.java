package com.nova.controller;

import com.nova.annotation.OptLog;
import com.nova.model.dto.ArticleAdminDTO;
import com.nova.model.dto.ArticleAdminViewDTO;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.vo.ArticleVO;
import com.nova.model.vo.ConditionVO;
import com.nova.model.vo.ResultVO;
import com.nova.service.ArticleService;
import com.nova.strategy.context.UploadStrategyContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.nova.constant.OptTypeConstant.SAVE_OR_UPDATE;
import static com.nova.constant.OptTypeConstant.UPLOAD;

@Api(tags = "文章模块")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private UploadStrategyContext uploadStrategyContext;

    @ApiOperation("获取后台文章")
    @GetMapping("/admin/articles")
    public ResultVO<PageResultDTO<ArticleAdminDTO>> listArticlesAdmin(ConditionVO conditionVO) {
        return ResultVO.ok(articleService.listArticlesAdmin(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation("保存和修改文章")
    @PostMapping("/admin/articles")
    public ResultVO<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO) {
        articleService.saveOrUpdateArticle(articleVO);
        return ResultVO.ok();
    }

    @OptLog(optType = UPLOAD)
    @ApiOperation("上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/admin/articles/images")
    public ResultVO<String> saveArticleImages(MultipartFile file) {
        return ResultVO.ok(uploadStrategyContext.executeUploadStrategy(file, "article/"));
    }

    @ApiOperation("根据id查看后台文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/admin/articles/{articleId}")
    public ResultVO<ArticleAdminViewDTO> getArticleBackById(@PathVariable("articleId") Integer articleId) {
        return ResultVO.ok(articleService.getArticleByIdAdmin(articleId));
    }

}
