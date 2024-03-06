package com.nova.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleAdminDTO {

    private Integer id;

    private String articleCover;

    private String articleTitle;

    private LocalDateTime createTime;

    private Integer viewsCount;

    private Integer categoryId;

    private List<Integer> tagIds;

    private Integer isTop;

    private Integer isFeatured;

    private Integer isDelete;

    private Integer status;

    private Integer type;

}
