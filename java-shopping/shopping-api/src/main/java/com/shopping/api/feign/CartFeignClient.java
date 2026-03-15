package com.shopping.api.feign;

import com.shopping.api.dto.CartItemDTO;
import com.shopping.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "shopping-cart", contextId = "cartFeignClient")
public interface CartFeignClient {

    @GetMapping("/api/cart/user/{userId}")
    Result<List<CartItemDTO>> getCartByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/api/cart/user/{userId}/count")
    Result<Integer> getCartCount(@PathVariable("userId") Long userId);

    @PostMapping("/api/cart/add")
    Result<CartItemDTO> addToCart(@RequestBody CartItemDTO cartItem);

    @DeleteMapping("/api/cart/user/{userId}/clear")
    Result<Boolean> clearCart(@PathVariable("userId") Long userId);

    @DeleteMapping("/api/cart/item/{itemId}")
    Result<Boolean> removeCartItem(@PathVariable("itemId") Long itemId);
}
