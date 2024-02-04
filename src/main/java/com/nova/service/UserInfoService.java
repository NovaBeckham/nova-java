package com.nova.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nova.entity.UserInfo;
import com.nova.model.dto.UserInfoDTO;

public interface UserInfoService extends IService<UserInfo> {

    UserInfoDTO getUserInfo();
}
