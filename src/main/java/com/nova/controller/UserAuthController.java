package com.nova.controller;

import com.nova.service.UserAuthService;
import com.nova.model.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "用户账号模块")
@RestController
public class UserAuthController {

    @Resource
    private UserAuthService userAuthService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/users/register")
    public ResultVO<?> register(@Valid @RequestBody UserVO userVO) {
        userAuthService.register(userVO);
        return ResultVO.ok();
    }

}
