package com.nova.controller;

import com.nova.annotation.OptLog;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.dto.TagAdminDTO;
import com.nova.model.dto.TagDTO;
import com.nova.model.vo.ConditionVO;
import com.nova.model.vo.ResultVO;
import com.nova.model.vo.TagVO;
import com.nova.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.nova.constant.OptTypeConstant.DELETE;
import static com.nova.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Api(tags = "标签模块")
@RestController
public class TagController {

    @Resource
    private TagService tagService;

    @ApiOperation("获取所有标签")
    @GetMapping("/tags/all")
    public ResultVO<List<TagDTO>> getAllTags() {
        return ResultVO.ok(tagService.listTags());
    }

    @ApiOperation(value = "查询后台标签列表")
    @GetMapping("/admin/tags")
    public ResultVO<PageResultDTO<TagAdminDTO>> listTagsAdmin(ConditionVO conditionVO) {
        return ResultVO.ok(tagService.listTagsAdmin(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "添加或修改标签")
    @PostMapping("/admin/tags")
    public ResultVO<?> saveOrUpdateTag(@Valid @RequestBody TagVO tagVO) {
        tagService.saveOrUpdateTag(tagVO);
        return ResultVO.ok();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public ResultVO<?> deleteTag(@RequestBody List<Integer> tagIdList) {
        tagService.deleteTag(tagIdList);
        return ResultVO.ok();
    }
}
