package com.shopping.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oms_order_item")
@Schema(description = "Order item entity")
public class OrderItem extends BaseEntity {

    @Schema(description = "Order ID")
    private Long orderId;

    @Schema(description = "Order number")
    private String orderNo;

    @Schema(description = "Product ID")
    private Long productId;

    @Schema(description = "Product name")
    private String productName;

    @Schema(description = "Product code")
    private String productCode;

    @Schema(description = "Product image")
    private String productImage;

    @Schema(description = "SKU ID")
    private Long skuId;

    @Schema(description = "SKU name")
    private String skuName;

    @Schema(description = "SKU code")
    private String skuCode;

    @Schema(description = "Unit price")
    private BigDecimal price;

    @Schema(description = "Quantity")
    private Integer quantity;

    @Schema(description = "Total amount")
    private BigDecimal totalAmount;

    @Schema(description = "Discount amount")
    private BigDecimal discountAmount;

    @Schema(description = "Real amount")
    private BigDecimal realAmount;

    @Schema(description = "Gift point")
    private Integer giftPoint;

    @Schema(description = "Gift growth")
    private Integer giftGrowth;
}
