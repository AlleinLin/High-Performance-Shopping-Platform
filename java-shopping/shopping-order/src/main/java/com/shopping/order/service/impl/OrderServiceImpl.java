package com.shopping.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.api.dto.InventoryLockRequest;
import com.shopping.api.dto.OrderDTO;
import com.shopping.api.dto.OrderItemDTO;
import com.shopping.api.dto.ProductDTO;
import com.shopping.api.feign.InventoryFeignClient;
import com.shopping.api.feign.ProductFeignClient;
import com.shopping.common.enums.OrderStatus;
import com.shopping.common.enums.PaymentMethod;
import com.shopping.common.exception.BusinessException;
import com.shopping.common.result.PageResult;
import com.shopping.common.result.ResultCode;
import com.shopping.order.entity.Order;
import com.shopping.order.entity.OrderItem;
import com.shopping.order.mapper.OrderItemMapper;
import com.shopping.order.mapper.OrderMapper;
import com.shopping.order.service.OrderService;
import com.shopping.order.vo.OrderCreateRequest;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final OrderItemMapper orderItemMapper;
    private final ProductFeignClient productFeignClient;
    private final InventoryFeignClient inventoryFeignClient;

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = this.getById(id);
        if (order == null) {
            return null;
        }
        return convertToDTO(order);
    }

    @Override
    public OrderDTO getOrderByOrderNo(String orderNo) {
        Order order = this.lambdaQuery()
                .eq(Order::getOrderNo, orderNo)
                .one();
        if (order == null) {
            return null;
        }
        return convertToDTO(order);
    }

    @Override
    public PageResult<OrderDTO> getOrderList(Long userId, Integer pageNum, Integer pageSize, Integer status) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            wrapper.eq(Order::getUserId, userId);
        }
        if (status != null) {
            wrapper.eq(Order::getStatus, status);
        }
        wrapper.orderByDesc(Order::getCreateTime);

        Page<Order> page = this.page(new Page<>(pageNum, pageSize), wrapper);

        return PageResult.of(
                page.getRecords().stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()),
                page.getTotal(),
                pageNum,
                pageSize
        );
    }

    @Override
    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public OrderDTO createOrder(OrderCreateRequest request) {
        String orderNo = generateOrderNo();
        
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        List<InventoryLockRequest.InventoryItem> inventoryItems = new ArrayList<>();
        
        for (OrderCreateRequest.OrderItemRequest itemRequest : request.getItems()) {
            ProductDTO product = productFeignClient.getProductById(itemRequest.getProductId()).getData();
            if (product == null) {
                throw new BusinessException(ResultCode.PRODUCT_NOT_FOUND);
            }
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(0L);
            orderItem.setOrderNo(orderNo);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductCode(product.getProductCode());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setSkuId(itemRequest.getSkuId());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setTotalAmount(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(orderItem.getTotalAmount());
            
            InventoryLockRequest.InventoryItem inventoryItem = new InventoryLockRequest.InventoryItem();
            inventoryItem.setProductId(itemRequest.getProductId());
            inventoryItem.setSkuId(itemRequest.getSkuId());
            inventoryItem.setQuantity(itemRequest.getQuantity());
            inventoryItems.add(inventoryItem);
        }
        
        InventoryLockRequest lockRequest = new InventoryLockRequest();
        lockRequest.setOrderNo(orderNo);
        lockRequest.setItems(inventoryItems);
        
        Boolean locked = inventoryFeignClient.lockInventory(lockRequest).getData();
        if (!Boolean.TRUE.equals(locked)) {
            throw new BusinessException(ResultCode.INVENTORY_NOT_ENOUGH);
        }
        
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(request.getUserId());
        order.setTotalAmount(totalAmount);
        order.setPaymentAmount(totalAmount);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setFreightAmount(BigDecimal.ZERO);
        order.setCouponId(request.getCouponId());
        order.setCouponAmount(BigDecimal.ZERO);
        order.setStatus(OrderStatus.PENDING_PAYMENT.getCode());
        order.setOrderType(request.getOrderType() != null ? request.getOrderType() : 0);
        order.setSourceType(request.getSourceType() != null ? request.getSourceType() : 0);
        order.setRemark(request.getRemark());
        order.setAutoConfirmDay(7);
        
        order.setReceiverName(request.getReceiverName());
        order.setReceiverPhone(request.getReceiverPhone());
        order.setReceiverProvince(request.getReceiverProvince());
        order.setReceiverCity(request.getReceiverCity());
        order.setReceiverDistrict(request.getReceiverDistrict());
        order.setReceiverAddress(request.getReceiverAddress());
        order.setReceiverZipCode(request.getReceiverZipCode());
        
        this.save(order);
        
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            orderItemMapper.insert(orderItem);
        }
        
        return convertToDTO(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean payOrder(Long id, Integer paymentMethod) {
        Order order = this.getById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        
        if (!OrderStatus.fromCode(order.getStatus()).canPay()) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR);
        }
        
        order.setStatus(OrderStatus.PAID.getCode());
        order.setPaymentMethod(paymentMethod);
        order.setPaymentTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        order.setPaymentTransactionId("PAY" + IdUtil.getSnowflakeNextIdStr());
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelOrder(Long id) {
        Order order = this.getById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        
        OrderStatus currentStatus = OrderStatus.fromCode(order.getStatus());
        if (!currentStatus.canCancel()) {
            throw new BusinessException(ResultCode.ORDER_CANNOT_CANCEL);
        }
        
        order.setStatus(OrderStatus.CANCELLED.getCode());
        
        List<OrderItem> items = orderItemMapper.selectList(
                new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, id)
        );
        
        List<InventoryLockRequest.InventoryItem> inventoryItems = items.stream()
                .map(item -> {
                    InventoryLockRequest.InventoryItem inventoryItem = new InventoryLockRequest.InventoryItem();
                    inventoryItem.setProductId(item.getProductId());
                    inventoryItem.setSkuId(item.getSkuId());
                    inventoryItem.setQuantity(item.getQuantity());
                    return inventoryItem;
                })
                .collect(Collectors.toList());
        
        InventoryLockRequest unlockRequest = new InventoryLockRequest();
        unlockRequest.setOrderNo(order.getOrderNo());
        unlockRequest.setItems(inventoryItems);
        inventoryFeignClient.unlockInventory(unlockRequest);
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deliverOrder(Long id, String deliveryCompany, String trackingNumber) {
        Order order = this.getById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        
        OrderStatus currentStatus = OrderStatus.fromCode(order.getStatus());
        if (!currentStatus.canShip()) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR);
        }
        
        order.setStatus(OrderStatus.SHIPPED.getCode());
        order.setDeliveryCompany(deliveryCompany);
        order.setTrackingNumber(trackingNumber);
        order.setDeliveryTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmReceive(Long id) {
        Order order = this.getById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        
        OrderStatus currentStatus = OrderStatus.fromCode(order.getStatus());
        if (!currentStatus.canConfirm()) {
            throw new BusinessException(ResultCode.ORDER_STATUS_ERROR);
        }
        
        order.setStatus(OrderStatus.DELIVERED.getCode());
        order.setReceiveTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        
        return this.updateById(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteOrder(Long id) {
        Order order = this.getById(id);
        if (order == null) {
            throw new BusinessException(ResultCode.ORDER_NOT_FOUND);
        }
        
        return this.removeById(id);
    }

    @Override
    public String generateOrderNo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) 
                + IdUtil.getSnowflakeNextIdStr().substring(10);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = BeanUtil.copyProperties(order, OrderDTO.class);
        
        List<OrderItem> items = orderItemMapper.selectList(
                new LambdaQueryWrapper<OrderItem>().eq(OrderItem::getOrderId, order.getId())
        );
        
        dto.setItems(items.stream()
                .map(item -> BeanUtil.copyProperties(item, OrderItemDTO.class))
                .collect(Collectors.toList()));
        
        return dto;
    }
}
