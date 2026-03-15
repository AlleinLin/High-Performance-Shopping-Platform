package com.shopping.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Product update request")
public class ProductUpdateRequest {

    @Schema(description = "Product name")
    private String name;

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

    @Schema(description = "Unit")
    private String unit;

    @Schema(description = "Weight (kg)")
    private BigDecimal weight;

    @Schema(description = "Sort order")
    private Integer sort;

    @Schema(description = "Brief description")
    private String brief;

    @Schema(description = "Product description (HTML)")
    private String description;

    @Schema(description = "Keywords for search")
    private String keywords;
}
