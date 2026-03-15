package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Product DTO")
public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Product ID")
    private Long id;

    @Schema(description = "Product name")
    private String name;

    @Schema(description = "Product code")
    private String productCode;

    @Schema(description = "Category ID")
    private Long categoryId;

    @Schema(description = "Category name")
    private String categoryName;

    @Schema(description = "Brand ID")
    private Long brandId;

    @Schema(description = "Brand name")
    private String brandName;

    @Schema(description = "Main image URL")
    private String mainImage;

    @Schema(description = "Price")
    private BigDecimal price;

    @Schema(description = "Original price")
    private BigDecimal originalPrice;

    @Schema(description = "Stock quantity")
    private Integer stock;

    @Schema(description = "Sales count")
    private Integer sales;

    @Schema(description = "Product status")
    private Integer status;

    @Schema(description = "Brief description")
    private String brief;

    @Schema(description = "Product description")
    private String description;
}
