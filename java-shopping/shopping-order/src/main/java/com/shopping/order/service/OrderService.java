package com.shopping.order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.api.dto.OrderDTO;
import com.shopping.common.result.PageResult;
import com.shopping.order.entity.Order;
import com.shopping.order.vo.OrderCreateRequest;

public interface OrderService extends IService<Order> {

    OrderDTO getOrderById(Long id);

    OrderDTO getOrderByOrderNo(String orderNo);

    PageResult<OrderDTO> getOrderList(Long userId, Integer pageNum, Integer pageSize, Integer status);

    OrderDTO createOrder(OrderCreateRequest request);

    Boolean payOrder(Long id, Integer paymentMethod);

    Boolean cancelOrder(Long id);

    Boolean deliverOrder(Long id, String deliveryCompany, String trackingNumber);

    Boolean confirmReceive(Long id);

    Boolean deleteOrder(Long id);

    String generateOrderNo();
}
