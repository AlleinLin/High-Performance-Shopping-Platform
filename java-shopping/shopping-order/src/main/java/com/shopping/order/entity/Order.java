package com.shopping.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oms_order")
@Schema(description = "Order entity")
public class Order extends BaseEntity {

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

    @Schema(description = "Coupon ID")
    private Long couponId;

    @Schema(description = "Coupon amount")
    private BigDecimal couponAmount;

    @Schema(description = "Order status")
    private Integer status;

    @Schema(description = "Payment method")
    private Integer paymentMethod;

    @Schema(description = "Payment time")
    private String paymentTime;

    @Schema(description = "Payment transaction ID")
    private String paymentTransactionId;

    @Schema(description = "Delivery time")
    private String deliveryTime;

    @Schema(description = "Delivery company")
    private String deliveryCompany;

    @Schema(description = "Tracking number")
    private String trackingNumber;

    @Schema(description = "Receive time")
    private String receiveTime;

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

    @Schema(description = "Order type (0: normal, 1: group buy, 2: flash sale)")
    private Integer orderType;

    @Schema(description = "Source type (0: PC, 1: H5, 2: APP, 3: Mini program)")
    private Integer sourceType;

    @Schema(description = "Remark")
    private String remark;

    @Schema(description = "Auto confirm days")
    private Integer autoConfirmDay;
}
