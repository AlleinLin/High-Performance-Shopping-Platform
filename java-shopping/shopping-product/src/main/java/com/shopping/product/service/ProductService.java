package com.shopping.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.api.dto.ProductDTO;
import com.shopping.common.result.PageResult;
import com.shopping.product.entity.Product;
import com.shopping.product.vo.ProductCreateRequest;
import com.shopping.product.vo.ProductUpdateRequest;

import java.util.List;

public interface ProductService extends IService<Product> {

    ProductDTO getProductById(Long id);

    List<ProductDTO> getProductsByIds(List<Long> ids);

    PageResult<ProductDTO> getProductList(Integer pageNum, Integer pageSize, Long categoryId, Long brandId, String keyword, Integer status);

    PageResult<ProductDTO> searchProducts(String keyword, Integer pageNum, Integer pageSize);

    ProductDTO createProduct(ProductCreateRequest request);

    ProductDTO updateProduct(Long id, ProductUpdateRequest request);

    Boolean updateStatus(Long id, Integer status);

    Boolean updateStock(Long id, Integer stock);

    Boolean deductStock(Long id, Integer quantity);

    Boolean addStock(Long id, Integer quantity);

    Integer getStock(Long id);

    List<ProductDTO> getHotProducts(Integer limit);

    List<ProductDTO> getNewProducts(Integer limit);

    List<ProductDTO> getRecommendProducts(Long userId, Integer limit);

    void syncToElasticsearch(Long productId);

    void syncAllToElasticsearch();
}
