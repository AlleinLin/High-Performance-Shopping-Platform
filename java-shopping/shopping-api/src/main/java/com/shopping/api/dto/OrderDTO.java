package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Order DTO")
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Order ID")
    private Long id;

    @Schema(description = "Order number")
    private String orderNo;

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "Total amount")
    private BigDecimal totalAmount;

    @Schema(description = "Payment amount")
    private BigDecimal paymentAmount;

    @Schema(description = "Discount amount")
    private BigDecimal discountAmount;

    @Schema(description = "Freight amount")
    private BigDecimal freightAmount;

    @Schema(description = "Order status")
    private Integer status;

    @Schema(description = "Payment method")
    private Integer paymentMethod;

    @Schema(description = "Payment time")
    private String paymentTime;

    @Schema(description = "Delivery time")
    private String deliveryTime;

    @Schema(description = "Receive time")
    private String receiveTime;

    @Schema(description = "Receiver name")
    private String receiverName;

    @Schema(description = "Receiver phone")
    private String receiverPhone;

    @Schema(description = "Receiver address")
    private String receiverAddress;

    @Schema(description = "Order items")
    private List<OrderItemDTO> items;
}
