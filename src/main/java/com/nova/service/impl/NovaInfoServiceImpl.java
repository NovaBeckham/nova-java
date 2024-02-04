package com.nova.service.impl;

import com.alibaba.fastjson.JSON;
import com.nova.mapper.WebsiteConfigMapper;
import com.nova.model.dto.WebsiteConfigDTO;
import com.nova.service.NovaInfoService;
import com.nova.service.RedisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class NovaInfoServiceImpl implements NovaInfoService {

    @Resource
    private WebsiteConfigMapper websiteConfigMapper;

    @Resource
    private RedisService redisService;

    @Override
    public WebsiteConfigDTO getWebsiteConfig() {
        WebsiteConfigDTO websiteConfigDTO;
        Object websiteConfig = redisService.get("website_config");
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigDTO = JSON.parseObject(websiteConfig.toString(), WebsiteConfigDTO.class);
        } else {
            String config = websiteConfigMapper.selectById(1).getConfig();
            websiteConfigDTO = JSON.parseObject(config, WebsiteConfigDTO.class);
            redisService.set("website_config", config);
        }
        return websiteConfigDTO;
    }
}
