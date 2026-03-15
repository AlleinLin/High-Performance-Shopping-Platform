package com.shopping.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_category")
@Schema(description = "Product category entity")
public class Category extends BaseEntity {

    @Schema(description = "Parent category ID")
    private Long parentId;

    @Schema(description = "Category name")
    private String name;

    @Schema(description = "Category level (1: first, 2: second, 3: third)")
    private Integer level;

    @Schema(description = "Category icon")
    private String icon;

    @Schema(description = "Category image")
    private String image;

    @Schema(description = "Sort order")
    private Integer sort;

    @Schema(description = "Is shown in navigation")
    private Boolean showStatus;

    @Schema(description = "Keywords for search")
    private String keywords;

    @Schema(description = "Description")
    private String description;
}
