package com.nova.handler;

import com.alibaba.fastjson.JSON;
import com.nova.constant.CommonConstant;
import com.nova.entity.UserAuth;
import com.nova.mapper.UserAuthMapper;
import com.nova.model.dto.UserDetailsDTO;
import com.nova.model.dto.UserInfoDTO;
import com.nova.model.vo.ResultVO;
import com.nova.service.TokenService;
import com.nova.utils.BeanCopyUtils;
import com.nova.utils.UserUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getUserDetailsDTO(), UserInfoDTO.class);
        if (Objects.nonNull(authentication)) {
            UserDetailsDTO userDetailsDTO = (UserDetailsDTO) authentication.getPrincipal();
            String token = tokenService.createToken(userDetailsDTO);
            userLoginDTO.setToken(token);
        }
        response.setContentType(CommonConstant.APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(ResultVO.ok(userLoginDTO)));
        updateUserInfo();
    }

    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getUserDetailsDTO().getId())
                .ipAddress(UserUtils.getUserDetailsDTO().getIpAddress())
                .ipSource(UserUtils.getUserDetailsDTO().getIpSource())
                .lastLoginTime(UserUtils.getUserDetailsDTO().getLastLoginTime())
                .build();
        userAuthMapper.updateById(userAuth);
    }
}
