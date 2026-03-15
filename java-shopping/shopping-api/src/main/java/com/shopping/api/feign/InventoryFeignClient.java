package com.shopping.api.feign;

import com.shopping.api.dto.InventoryDTO;
import com.shopping.api.dto.InventoryLockRequest;
import com.shopping.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "shopping-inventory", contextId = "inventoryFeignClient")
public interface InventoryFeignClient {

    @GetMapping("/api/inventory/product/{productId}")
    Result<InventoryDTO> getInventoryByProductId(@PathVariable("productId") Long productId);

    @GetMapping("/api/inventory/product/{productId}/sku/{skuId}")
    Result<InventoryDTO> getInventoryBySkuId(@PathVariable("productId") Long productId, 
                                              @PathVariable("skuId") Long skuId);

    @PostMapping("/api/inventory/lock")
    Result<Boolean> lockInventory(@RequestBody InventoryLockRequest request);

    @PostMapping("/api/inventory/unlock")
    Result<Boolean> unlockInventory(@RequestBody InventoryLockRequest request);

    @PostMapping("/api/inventory/deduct")
    Result<Boolean> deductInventory(@RequestBody InventoryLockRequest request);
}
