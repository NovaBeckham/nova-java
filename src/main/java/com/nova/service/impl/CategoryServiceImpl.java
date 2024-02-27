package com.nova.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nova.entity.Article;
import com.nova.entity.Category;
import com.nova.exception.BizException;
import com.nova.mapper.ArticleMapper;
import com.nova.mapper.CategoryMapper;
import com.nova.model.dto.CategoryAdminDTO;
import com.nova.model.dto.CategoryDTO;
import com.nova.model.dto.CategoryOptionDTO;
import com.nova.model.dto.PageResultDTO;
import com.nova.model.vo.CategoryVO;
import com.nova.model.vo.ConditionVO;
import com.nova.service.CategoryService;
import com.nova.utils.BeanCopyUtils;
import com.nova.utils.PageUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    public List<CategoryDTO> listCategories() {
        return categoryMapper.listCategories();
    }

    @SneakyThrows
    @Override
    public PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO) {
        Integer total = categoryMapper.selectCount(new LambdaQueryWrapper<Category>().like(StringUtils.isNotBlank(conditionVO.getKeywords()), Category::getCategoryName, conditionVO.getKeywords()));
        if (total == 0) {
            return new PageResultDTO<>();
        }
        List<CategoryAdminDTO> categoryList = categoryMapper.listCategoriesAdmin(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVO);
        return new PageResultDTO<>(categoryList, total, PageUtils.getCurrent(), PageUtils.getSize());
    }

    @SneakyThrows
    @Override
    public List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO) {
        List<Category> categoryList = categoryMapper.selectList(new LambdaQueryWrapper<Category>().like(StringUtils.isNotBlank(conditionVO.getKeywords()), Category::getCategoryName, conditionVO.getKeywords()).orderByDesc(Category::getId));
        return BeanCopyUtils.copyList(categoryList, CategoryOptionDTO.class);
    }

    @Override
    public void deleteCategories(List<Integer> categoryIds) {
        Integer count = articleMapper.selectCount(new LambdaQueryWrapper<Article>().in(Article::getCategoryId, categoryIds));
        if (count > 0) {
            throw new BizException("删除失败，该分类下存在文章");
        }
        categoryMapper.deleteBatchIds(categoryIds);
    }

    @Override
    public void saveOrUpdateCategory(CategoryVO categoryVO) {
        Category existCategory = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().select(Category::getId).eq(Category::getCategoryName, categoryVO.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVO.getId())) {
            throw new BizException("分类名已存在");
        }
        Category category = Category.builder().id(categoryVO.getId()).categoryName(categoryVO.getCategoryName()).build();
        this.saveOrUpdate(category);
    }
}
