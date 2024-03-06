package com.nova.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "文章")
public class ArticleAdminViewDTO {

    private Integer id;

    private String articleTitle;

    private String articleAbstract;

    private String articleContent;

    private String articleCover;

    private Integer categoryId;

    private List<Integer> tagIds;

    private Integer isTop;

    private Integer isFeatured;

    private Integer status;

    private Integer type;

    private String originalUrl;

    private String password;

}
