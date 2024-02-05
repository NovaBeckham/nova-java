package com.nova.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nova.entity.Category;
import com.nova.model.dto.CategoryAdminDTO;
import com.nova.model.dto.CategoryDTO;
import com.nova.model.dto.CategoryOptionDTO;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.vo.CategoryVO;
import com.nova.model.vo.ConditionVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();

    PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO);

    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

    void deleteCategories(List<Integer> categoryIds);

    void saveOrUpdateCategory(CategoryVO categoryVO);

}
