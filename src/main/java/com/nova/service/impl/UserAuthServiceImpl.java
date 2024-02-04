package com.nova.service.impl;

import com.nova.constant.CommonConstant;
import com.nova.entity.UserAuth;
import com.nova.entity.UserInfo;
import com.nova.entity.UserRole;
import com.nova.exception.BizException;
import com.nova.mapper.UserAuthMapper;
import com.nova.mapper.UserInfoMapper;
import com.nova.mapper.UserRoleMapper;
import com.nova.service.NovaInfoService;
import com.nova.service.TokenService;
import com.nova.service.UserAuthService;
import com.nova.model.vo.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.nova.utils.CommonUtils.checkEmail;


@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private NovaInfoService novaInfoService;

    @Resource
    private TokenService tokenService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserVO userVO) {
        if (!checkEmail(userVO.getUsername())) {
            throw new BizException("邮箱格式不对!");
        }
        if (checkUser(userVO)) {
            throw new BizException("邮箱已被注册！");
        }
        UserInfo userInfo = UserInfo.builder()
                .email(userVO.getUsername())
                .nickname(CommonConstant.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(novaInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoMapper.insert(userInfo);
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(1)
                .build();
        userRoleMapper.insert(userRole);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(userVO.getUsername())
                .password(BCrypt.hashpw(userVO.getPassword(), BCrypt.gensalt()))
                .loginType(1)
                .build();
        userAuthMapper.insert(userAuth);
    }

    private Boolean checkUser(UserVO user) {
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

}
