package com.shopping.api.fallback;

import com.shopping.api.dto.ProductDTO;
import com.shopping.api.feign.ProductFeignClient;
import com.shopping.common.result.Result;
import com.shopping.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductFeignFallback implements ProductFeignClient {

    @Override
    public Result<ProductDTO> getProductById(Long id) {
        log.error("Product service unavailable, getProductById: {}", id);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<List<ProductDTO>> getProductsByIds(List<Long> ids) {
        log.error("Product service unavailable, getProductsByIds: {}", ids);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<Integer> getStock(Long id) {
        log.error("Product service unavailable, getStock: {}", id);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<Boolean> deductStock(Long id, Integer quantity) {
        log.error("Product service unavailable, deductStock: {}, {}", id, quantity);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<Boolean> addStock(Long id, Integer quantity) {
        log.error("Product service unavailable, addStock: {}, {}", id, quantity);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }
}
