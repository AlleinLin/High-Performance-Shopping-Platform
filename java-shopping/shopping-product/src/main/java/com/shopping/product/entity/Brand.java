package com.shopping.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_brand")
@Schema(description = "Brand entity")
public class Brand extends BaseEntity {

    @Schema(description = "Brand name")
    private String name;

    @Schema(description = "Brand first letter")
    private String firstLetter;

    @Schema(description = "Brand logo")
    private String logo;

    @Schema(description = "Brand description")
    private String description;

    @Schema(description = "Sort order")
    private Integer sort;

    @Schema(description = "Is shown")
    private Boolean showStatus;

    @Schema(description = "Factory status")
    private Integer factoryStatus;
}
