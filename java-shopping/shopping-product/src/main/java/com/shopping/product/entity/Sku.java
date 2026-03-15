package com.shopping.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("pms_sku")
@Schema(description = "SKU entity")
public class Sku extends BaseEntity {

    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "SKU code")
    private String skuCode;

    @Schema(description = "SKU name")
    private String name;

    @Schema(description = "Price")
    private BigDecimal price;

    @Schema(description = "Stock quantity")
    private Integer stock;

    @Schema(description = "Low stock threshold")
    private Integer lowStock;

    @Schema(description = "Sales count")
    private Integer sales;

    @Schema(description = "Image URL")
    private String image;

    @Schema(description = "Specification values (JSON)")
    private String specValues;

    @Schema(description = "SKU status")
    private Integer status;
}
