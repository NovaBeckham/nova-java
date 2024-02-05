package com.nova.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nova.entity.Category;
import com.nova.model.dto.CategoryAdminDTO;
import com.nova.model.dto.CategoryDTO;
import com.nova.model.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryDTO> listCategories();

    List<CategoryAdminDTO> listCategoriesAdmin(@Param("current") Long current, @Param("size") Long size, @Param("conditionVO") ConditionVO conditionVO);

}
