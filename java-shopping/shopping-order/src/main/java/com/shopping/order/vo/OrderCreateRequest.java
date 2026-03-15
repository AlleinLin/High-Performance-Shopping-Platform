package com.shopping.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "Order create request")
public class OrderCreateRequest {

    @NotNull(message = "User ID cannot be empty")
    @Schema(description = "User ID", required = true)
    private Long userId;

    @NotEmpty(message = "Order items cannot be empty")
    @Schema(description = "Order items", required = true)
    private List<OrderItemRequest> items;

    @Schema(description = "Coupon ID")
    private Long couponId;

    @Schema(description = "Address ID")
    private Long addressId;

    @Schema(description = "Receiver name")
    private String receiverName;

    @Schema(description = "Receiver phone")
    private String receiverPhone;

    @Schema(description = "Receiver province")
    private String receiverProvince;

    @Schema(description = "Receiver city")
    private String receiverCity;

    @Schema(description = "Receiver district")
    private String receiverDistrict;

    @Schema(description = "Receiver address")
    private String receiverAddress;

    @Schema(description = "Receiver zip code")
    private String receiverZipCode;

    @Schema(description = "Remark")
    private String remark;

    @Schema(description = "Order type (0: normal, 1: group buy, 2: flash sale)")
    private Integer orderType;

    @Schema(description = "Source type (0: PC, 1: H5, 2: APP, 3: Mini program)")
    private Integer sourceType;

    @Data
    @Schema(description = "Order item request")
    public static class OrderItemRequest {

        @NotNull(message = "Product ID cannot be empty")
        @Schema(description = "Product ID", required = true)
        private Long productId;

        @Schema(description = "SKU ID")
        private Long skuId;

        @NotNull(message = "Quantity cannot be empty")
        @Schema(description = "Quantity", required = true)
        private Integer quantity;
    }
}
