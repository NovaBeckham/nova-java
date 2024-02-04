package com.nova.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nova.entity.UserInfo;
import com.nova.mapper.UserInfoMapper;
import com.nova.model.dto.UserInfoDTO;
import com.nova.service.UserInfoService;
import com.nova.utils.BeanCopyUtils;
import com.nova.utils.UserUtils;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public UserInfoDTO getUserInfo() {
        return BeanCopyUtils.copyObject(UserUtils.getUserDetailsDTO(), UserInfoDTO.class);
    }
}
