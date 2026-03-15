package com.shopping.product.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Product create request")
public class ProductCreateRequest {

    @NotBlank(message = "Product name cannot be empty")
    @Schema(description = "Product name", required = true)
    private String name;

    @Schema(description = "Product code")
    private String productCode;

    @NotNull(message = "Category ID cannot be empty")
    @Schema(description = "Category ID", required = true)
    private Long categoryId;

    @Schema(description = "Brand ID")
    private Long brandId;

    @Schema(description = "Main image URL")
    private String mainImage;

    @Schema(description = "Sub images (JSON array)")
    private String subImages;

    @NotNull(message = "Price cannot be empty")
    @Positive(message = "Price must be positive")
    @Schema(description = "Price", required = true)
    private BigDecimal price;

    @Schema(description = "Original price")
    private BigDecimal originalPrice;

    @NotNull(message = "Stock cannot be empty")
    @Positive(message = "Stock must be positive")
    @Schema(description = "Stock quantity", required = true)
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
