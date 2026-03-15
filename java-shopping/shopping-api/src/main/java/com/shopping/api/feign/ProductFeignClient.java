package com.shopping.api.feign;

import com.shopping.api.dto.ProductDTO;
import com.shopping.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "shopping-product", contextId = "productFeignClient")
public interface ProductFeignClient {

    @GetMapping("/api/product/{id}")
    Result<ProductDTO> getProductById(@PathVariable("id") Long id);

    @PostMapping("/api/product/batch")
    Result<List<ProductDTO>> getProductsByIds(@RequestBody List<Long> ids);

    @GetMapping("/api/product/{id}/stock")
    Result<Integer> getStock(@PathVariable("id") Long id);

    @PostMapping("/api/product/{id}/stock/deduct")
    Result<Boolean> deductStock(@PathVariable("id") Long id, @RequestBody Integer quantity);

    @PostMapping("/api/product/{id}/stock/add")
    Result<Boolean> addStock(@PathVariable("id") Long id, @RequestBody Integer quantity);
}
