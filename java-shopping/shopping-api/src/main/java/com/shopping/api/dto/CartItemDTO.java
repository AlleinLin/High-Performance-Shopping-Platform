package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(description = "Cart Item DTO")
public class CartItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Cart item ID")
    private Long id;

    @Schema(description = "User ID")
    private Long userId;

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

    @Schema(description = "Price")
    private BigDecimal price;

    @Schema(description = "Quantity")
    private Integer quantity;

    @Schema(description = "Checked")
    private Boolean checked;

    @Schema(description = "Stock")
    private Integer stock;

    @Schema(description = "Product status")
    private Integer productStatus;
}
