package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Inventory Lock Request")
public class InventoryLockRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Order number")
    private String orderNo;

    @Schema(description = "Items to lock")
    private List<InventoryItem> items;

    @Data
    @Schema(description = "Inventory item")
    public static class InventoryItem implements Serializable {
        
        private static final long serialVersionUID = 1L;

        @Schema(description = "Product ID")
        private Long productId;

        @Schema(description = "SKU ID")
        private Long skuId;

        @Schema(description = "Quantity")
        private Integer quantity;
    }
}
