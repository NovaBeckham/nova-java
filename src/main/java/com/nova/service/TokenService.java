package com.nova.service;

import com.nova.model.dto.UserDetailsDTO;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    String createToken(UserDetailsDTO userDetailsDTO);

    String createToken(String subject);

    void refreshToken(UserDetailsDTO userDetailsDTO);

    void renewToken(UserDetailsDTO userDetailsDTO);

    Claims parseToken(String token);

    UserDetailsDTO getUserDetailDTO(HttpServletRequest request);

}
