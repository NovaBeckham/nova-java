package com.nova.controller;

import com.nova.model.dto.UserInfoDTO;
import com.nova.model.vo.ResultVO;
import com.nova.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("获取用户信息")
    @GetMapping("/users/info")
    public ResultVO<UserInfoDTO> getUserInfo() {
        return ResultVO.ok(userInfoService.getUserInfo());
    }
}
