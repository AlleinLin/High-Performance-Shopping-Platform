package com.shopping.api.feign;

import com.shopping.api.dto.OrderDTO;
import com.shopping.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "shopping-order", contextId = "orderFeignClient")
public interface OrderFeignClient {

    @GetMapping("/api/order/{id}")
    Result<OrderDTO> getOrderById(@PathVariable("id") Long id);

    @GetMapping("/api/order/no/{orderNo}")
    Result<OrderDTO> getOrderByOrderNo(@PathVariable("orderNo") String orderNo);

    @PostMapping("/api/order/create")
    Result<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO);

    @PostMapping("/api/order/{id}/pay")
    Result<Boolean> payOrder(@PathVariable("id") Long id, @RequestParam("paymentMethod") Integer paymentMethod);

    @PostMapping("/api/order/{id}/cancel")
    Result<Boolean> cancelOrder(@PathVariable("id") Long id);

    @PostMapping("/api/order/{id}/deliver")
    Result<Boolean> deliverOrder(@PathVariable("id") Long id, @RequestParam("trackingNo") String trackingNo);
}
