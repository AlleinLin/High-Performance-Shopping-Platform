package com.shopping.order.controller;

import com.shopping.api.dto.OrderDTO;
import com.shopping.common.annotation.Idempotent;
import com.shopping.common.annotation.RateLimit;
import com.shopping.common.result.PageResult;
import com.shopping.common.result.Result;
import com.shopping.order.service.OrderService;
import com.shopping.order.vo.OrderCreateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order API", description = "Order management APIs")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get order by ID", description = "Retrieve order details by order ID")
    @GetMapping("/{id}")
    public Result<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return order != null ? Result.success(order) : Result.failed("Order not found");
    }

    @Operation(summary = "Get order by order number", description = "Retrieve order details by order number")
    @GetMapping("/no/{orderNo}")
    public Result<OrderDTO> getOrderByOrderNo(@PathVariable String orderNo) {
        OrderDTO order = orderService.getOrderByOrderNo(orderNo);
        return order != null ? Result.success(order) : Result.failed("Order not found");
    }

    @Operation(summary = "Get order list", description = "Retrieve paginated order list")
    @GetMapping("/list")
    public Result<PageResult<OrderDTO>> getOrderList(
            @Parameter(description = "User ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "Order status") @RequestParam(required = false) Integer status) {
        return Result.success(orderService.getOrderList(userId, pageNum, pageSize, status));
    }

    @Operation(summary = "Create order", description = "Create a new order")
    @PostMapping("/create")
    @Idempotent(key = "createOrder", expireTime = 10)
    @RateLimit(key = "createOrder", limit = 10, period = 60)
    public Result<OrderDTO> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return Result.success(orderService.createOrder(request));
    }

    @Operation(summary = "Pay order", description = "Process order payment")
    @PostMapping("/{id}/pay")
    public Result<Boolean> payOrder(
            @PathVariable Long id,
            @Parameter(description = "Payment method (1: Alipay, 2: WeChat, 3: Bank card, 4: Balance)") 
            @RequestParam Integer paymentMethod) {
        return Result.success(orderService.payOrder(id, paymentMethod));
    }

    @Operation(summary = "Cancel order", description = "Cancel an unpaid or pending order")
    @PostMapping("/{id}/cancel")
    public Result<Boolean> cancelOrder(@PathVariable Long id) {
        return Result.success(orderService.cancelOrder(id));
    }

    @Operation(summary = "Deliver order", description = "Mark order as shipped")
    @PostMapping("/{id}/deliver")
    public Result<Boolean> deliverOrder(
            @PathVariable Long id,
            @Parameter(description = "Delivery company") @RequestParam String deliveryCompany,
            @Parameter(description = "Tracking number") @RequestParam String trackingNumber) {
        return Result.success(orderService.deliverOrder(id, deliveryCompany, trackingNumber));
    }

    @Operation(summary = "Confirm receive", description = "Confirm order receipt by customer")
    @PostMapping("/{id}/confirm")
    public Result<Boolean> confirmReceive(@PathVariable Long id) {
        return Result.success(orderService.confirmReceive(id));
    }

    @Operation(summary = "Delete order", description = "Delete order (soft delete)")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteOrder(@PathVariable Long id) {
        return Result.success(orderService.deleteOrder(id));
    }
}
