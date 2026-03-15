package com.shopping.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_product")
@Schema(description = "Product entity")
public class Product extends BaseEntity {

    @Schema(description = "Product name")
    private String name;

    @Schema(description = "Product code")
    private String productCode;

    @Schema(description = "Category ID")
    private Long categoryId;

    @Schema(description = "Brand ID")
    private Long brandId;

    @Schema(description = "Main image URL")
    private String mainImage;

    @Schema(description = "Sub images (JSON array)")
    private String subImages;

    @Schema(description = "Price")
    private BigDecimal price;

    @Schema(description = "Original price")
    private BigDecimal originalPrice;

    @Schema(description = "Stock quantity")
    private Integer stock;

    @Schema(description = "Low stock threshold")
    private Integer lowStock;

    @Schema(description = "Sales count")
    private Integer sales;

    @Schema(description = "Unit")
    private String unit;

    @Schema(description = "Weight (kg)")
    private BigDecimal weight;

    @Schema(description = "Sort order")
    private Integer sort;

    @Schema(description = "Product status (0: draft, 1: pending, 2: on shelf, 3: off shelf, 4: deleted)")
    private Integer status;

    @Schema(description = "Brief description")
    private String brief;

    @Schema(description = "Product description (HTML)")
    private String description;

    @Schema(description = "Keywords for search")
    private String keywords;

    @Schema(description = "Note")
    private String note;
}
