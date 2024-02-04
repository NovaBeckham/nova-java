package com.nova.mapper;

import com.nova.entity.UserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nova.model.dto.UserAuthDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthMapper extends BaseMapper<UserAuth> {

    UserAuthDTO UserInfoByUsername(@Param("username") String username);

}
