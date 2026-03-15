package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Order Item DTO")
public class OrderItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Order item ID")
    private Long id;

    @Schema(description = "Order ID")
    private Long orderId;

    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "Product name")
    private String productName;

    @Schema(description = "Product image")
    private String productImage;

    @Schema(description = "SKU ID")
    private Long skuId;

    @Schema(description = "SKU name")
    private String skuName;

    @Schema(description = "Unit price")
    private BigDecimal price;

    @Schema(description = "Quantity")
    private Integer quantity;

    @Schema(description = "Total amount")
    private BigDecimal totalAmount;
}
