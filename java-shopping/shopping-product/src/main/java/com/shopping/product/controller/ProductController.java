package com.shopping.product.controller;

import com.shopping.api.dto.ProductDTO;
import com.shopping.common.annotation.RateLimit;
import com.shopping.common.result.PageResult;
import com.shopping.common.result.Result;
import com.shopping.product.service.ProductService;
import com.shopping.product.vo.ProductCreateRequest;
import com.shopping.product.vo.ProductUpdateRequest;
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

import java.util.List;

@Tag(name = "Product API", description = "Product management APIs")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get product by ID", description = "Retrieve product details by product ID")
    @GetMapping("/{id}")
    public Result<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return product != null ? Result.success(product) : Result.failed("Product not found");
    }

    @Operation(summary = "Get products by IDs", description = "Retrieve multiple products by their IDs")
    @PostMapping("/batch")
    public Result<List<ProductDTO>> getProductsByIds(@RequestBody List<Long> ids) {
        return Result.success(productService.getProductsByIds(ids));
    }

    @Operation(summary = "Get product list", description = "Retrieve paginated product list with filters")
    @GetMapping("/list")
    public Result<PageResult<ProductDTO>> getProductList(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "Category ID") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Brand ID") @RequestParam(required = false) Long brandId,
            @Parameter(description = "Search keyword") @RequestParam(required = false) String keyword,
            @Parameter(description = "Product status") @RequestParam(required = false) Integer status) {
        return Result.success(productService.getProductList(pageNum, pageSize, categoryId, brandId, keyword, status));
    }

    @Operation(summary = "Search products", description = "Search products by keyword")
    @GetMapping("/search")
    @RateLimit(key = "search", limit = 100, period = 60)
    public Result<PageResult<ProductDTO>> searchProducts(
            @Parameter(description = "Search keyword") @RequestParam(required = false) String keyword,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(productService.searchProducts(keyword, pageNum, pageSize));
    }

    @Operation(summary = "Create product", description = "Create a new product")
    @PostMapping
    public Result<ProductDTO> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return Result.success(productService.createProduct(request));
    }

    @Operation(summary = "Update product", description = "Update product information")
    @PutMapping("/{id}")
    public Result<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return Result.success(productService.updateProduct(id, request));
    }

    @Operation(summary = "Update product status", description = "Update product status (on/off shelf)")
    @PutMapping("/{id}/status")
    public Result<Boolean> updateStatus(
            @PathVariable Long id,
            @Parameter(description = "Status (0: draft, 1: pending, 2: on shelf, 3: off shelf)") @RequestParam Integer status) {
        return Result.success(productService.updateStatus(id, status));
    }

    @Operation(summary = "Get product stock", description = "Get product stock quantity")
    @GetMapping("/{id}/stock")
    public Result<Integer> getStock(@PathVariable Long id) {
        return Result.success(productService.getStock(id));
    }

    @Operation(summary = "Deduct product stock", description = "Deduct product stock (for order creation)")
    @PostMapping("/{id}/stock/deduct")
    public Result<Boolean> deductStock(
            @PathVariable Long id,
            @Parameter(description = "Quantity to deduct") @RequestParam Integer quantity) {
        return Result.success(productService.deductStock(id, quantity));
    }

    @Operation(summary = "Add product stock", description = "Add product stock (for order cancellation)")
    @PostMapping("/{id}/stock/add")
    public Result<Boolean> addStock(
            @PathVariable Long id,
            @Parameter(description = "Quantity to add") @RequestParam Integer quantity) {
        return Result.success(productService.addStock(id, quantity));
    }

    @Operation(summary = "Get hot products", description = "Get hot selling products")
    @GetMapping("/hot")
    public Result<List<ProductDTO>> getHotProducts(
            @Parameter(description = "Limit") @RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(productService.getHotProducts(limit));
    }

    @Operation(summary = "Get new products", description = "Get newly added products")
    @GetMapping("/new")
    public Result<List<ProductDTO>> getNewProducts(
            @Parameter(description = "Limit") @RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(productService.getNewProducts(limit));
    }

    @Operation(summary = "Get recommended products", description = "Get personalized recommended products")
    @GetMapping("/recommend")
    public Result<List<ProductDTO>> getRecommendProducts(
            @Parameter(description = "User ID") @RequestParam(required = false) Long userId,
            @Parameter(description = "Limit") @RequestParam(defaultValue = "10") Integer limit) {
        return Result.success(productService.getRecommendProducts(userId, limit));
    }

    @Operation(summary = "Sync product to Elasticsearch", description = "Sync single product to search engine")
    @PostMapping("/{id}/sync")
    public Result<Void> syncToElasticsearch(@PathVariable Long id) {
        productService.syncToElasticsearch(id);
        return Result.success();
    }

    @Operation(summary = "Sync all products to Elasticsearch", description = "Sync all products to search engine")
    @PostMapping("/sync-all")
    public Result<Void> syncAllToElasticsearch() {
        productService.syncAllToElasticsearch();
        return Result.success();
    }
}
