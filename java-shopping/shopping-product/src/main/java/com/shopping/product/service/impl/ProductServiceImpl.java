package com.shopping.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.api.dto.ProductDTO;
import com.shopping.common.constant.RedisConstants;
import com.shopping.common.enums.ProductStatus;
import com.shopping.common.exception.BusinessException;
import com.shopping.common.result.PageResult;
import com.shopping.common.result.ResultCode;
import com.shopping.product.entity.Brand;
import com.shopping.product.entity.Category;
import com.shopping.product.entity.Product;
import com.shopping.product.mapper.BrandMapper;
import com.shopping.product.mapper.CategoryMapper;
import com.shopping.product.mapper.ProductMapper;
import com.shopping.product.service.ProductService;
import com.shopping.product.vo.ProductCreateRequest;
import com.shopping.product.vo.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    private final CategoryMapper categoryMapper;
    private final BrandMapper brandMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public ProductDTO getProductById(Long id) {
        String cacheKey = RedisConstants.PRODUCT_INFO_PREFIX + id;
        String cached = redisTemplate.opsForValue().get(cacheKey);
        
        Product product = this.getById(id);
        if (product == null) {
            return null;
        }
        
        ProductDTO dto = convertToDTO(product);
        redisTemplate.opsForValue().set(cacheKey, BeanUtil.beanToString(dto), 
                RedisConstants.PRODUCT_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
        
        return dto;
    }

    @Override
    public List<ProductDTO> getProductsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<Product> products = this.listByIds(ids);
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PageResult<ProductDTO> getProductList(Integer pageNum, Integer pageSize, Long categoryId, Long brandId, String keyword, Integer status) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        
        if (categoryId != null) {
            wrapper.eq(Product::getCategoryId, categoryId);
        }
        if (brandId != null) {
            wrapper.eq(Product::getBrandId, brandId);
        }
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Product::getName, keyword)
                    .or().like(Product::getKeywords, keyword)
                    .or().like(Product::getBrief, keyword));
        }
        if (status != null) {
            wrapper.eq(Product::getStatus, status);
        }
        
        wrapper.orderByDesc(Product::getCreateTime);
        
        Page<Product> page = this.page(new Page<>(pageNum, pageSize), wrapper);
        
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
    public PageResult<ProductDTO> searchProducts(String keyword, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, ProductStatus.ON_SHELF.getCode());
        
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(Product::getName, keyword)
                    .or().like(Product::getKeywords, keyword)
                    .or().like(Product::getBrief, keyword));
        }
        
        wrapper.orderByDesc(Product::getSales);
        
        Page<Product> page = this.page(new Page<>(pageNum, pageSize), wrapper);
        
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
    @Transactional(rollbackFor = Exception.class)
    public ProductDTO createProduct(ProductCreateRequest request) {
        Product product = BeanUtil.copyProperties(request, Product.class);
        product.setStatus(ProductStatus.DRAFT.getCode());
        product.setSales(0);
        
        if (!StringUtils.hasText(product.getProductCode())) {
            product.setProductCode("P" + System.currentTimeMillis());
        }
        
        this.save(product);
        
        return convertToDTO(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDTO updateProduct(Long id, ProductUpdateRequest request) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_FOUND);
        }
        
        BeanUtil.copyProperties(request, product, "id");
        this.updateById(product);
        
        redisTemplate.delete(RedisConstants.PRODUCT_INFO_PREFIX + id);
        
        return convertToDTO(product);
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_FOUND);
        }
        
        product.setStatus(status);
        boolean result = this.updateById(product);
        
        if (result) {
            redisTemplate.delete(RedisConstants.PRODUCT_INFO_PREFIX + id);
        }
        
        return result;
    }

    @Override
    public Boolean updateStock(Long id, Integer stock) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_FOUND);
        }
        
        product.setStock(stock);
        boolean result = this.updateById(product);
        
        if (result) {
            redisTemplate.delete(RedisConstants.PRODUCT_STOCK_PREFIX + id);
            redisTemplate.delete(RedisConstants.PRODUCT_INFO_PREFIX + id);
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deductStock(Long id, Integer quantity) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_FOUND);
        }
        
        if (product.getStock() < quantity) {
            throw new BusinessException(ResultCode.PRODUCT_STOCK_NOT_ENOUGH);
        }
        
        product.setStock(product.getStock() - quantity);
        product.setSales(product.getSales() + quantity);
        
        boolean result = this.updateById(product);
        
        if (result) {
            redisTemplate.delete(RedisConstants.PRODUCT_STOCK_PREFIX + id);
            redisTemplate.delete(RedisConstants.PRODUCT_INFO_PREFIX + id);
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addStock(Long id, Integer quantity) {
        Product product = this.getById(id);
        if (product == null) {
            throw new BusinessException(ResultCode.PRODUCT_NOT_FOUND);
        }
        
        product.setStock(product.getStock() + quantity);
        
        boolean result = this.updateById(product);
        
        if (result) {
            redisTemplate.delete(RedisConstants.PRODUCT_STOCK_PREFIX + id);
            redisTemplate.delete(RedisConstants.PRODUCT_INFO_PREFIX + id);
        }
        
        return result;
    }

    @Override
    public Integer getStock(Long id) {
        String cacheKey = RedisConstants.PRODUCT_STOCK_PREFIX + id;
        String cached = redisTemplate.opsForValue().get(cacheKey);
        
        if (StringUtils.hasText(cached)) {
            return Integer.parseInt(cached);
        }
        
        Product product = this.getById(id);
        if (product == null) {
            return 0;
        }
        
        redisTemplate.opsForValue().set(cacheKey, String.valueOf(product.getStock()), 
                RedisConstants.PRODUCT_INFO_EXPIRE_TIME, TimeUnit.SECONDS);
        
        return product.getStock();
    }

    @Override
    public List<ProductDTO> getHotProducts(Integer limit) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, ProductStatus.ON_SHELF.getCode())
                .orderByDesc(Product::getSales)
                .last("LIMIT " + limit);
        
        return this.list(wrapper).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getNewProducts(Integer limit) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getStatus, ProductStatus.ON_SHELF.getCode())
                .orderByDesc(Product::getCreateTime)
                .last("LIMIT " + limit);
        
        return this.list(wrapper).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getRecommendProducts(Long userId, Integer limit) {
        return getHotProducts(limit);
    }

    @Override
    public void syncToElasticsearch(Long productId) {
        log.info("Syncing product {} to Elasticsearch", productId);
    }

    @Override
    public void syncAllToElasticsearch() {
        log.info("Syncing all products to Elasticsearch");
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = BeanUtil.copyProperties(product, ProductDTO.class);
        
        if (product.getCategoryId() != null) {
            Category category = categoryMapper.selectById(product.getCategoryId());
            if (category != null) {
                dto.setCategoryName(category.getName());
            }
        }
        
        if (product.getBrandId() != null) {
            Brand brand = brandMapper.selectById(product.getBrandId());
            if (brand != null) {
                dto.setBrandName(brand.getName());
            }
        }
        
        return dto;
    }
}
