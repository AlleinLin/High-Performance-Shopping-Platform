package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Inventory DTO")
public class InventoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "SKU ID")
    private Long skuId;

    @Schema(description = "Available stock")
    private Integer availableStock;

    @Schema(description = "Locked stock")
    private Integer lockedStock;

    @Schema(description = "Total stock")
    private Integer totalStock;

    @Schema(description = "Low stock threshold")
    private Integer lowStockThreshold;

    @Schema(description = "Is low stock")
    private Boolean isLowStock;
}
