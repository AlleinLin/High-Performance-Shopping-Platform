package com.shopping.api.fallback;

import com.shopping.api.dto.UserDTO;
import com.shopping.api.feign.UserFeignClient;
import com.shopping.common.result.Result;
import com.shopping.common.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignFallback implements UserFeignClient {

    @Override
    public Result<UserDTO> getUserById(Long id) {
        log.error("User service unavailable, getUserById: {}", id);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<UserDTO> getUserByUsername(String username) {
        log.error("User service unavailable, getUserByUsername: {}", username);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<Boolean> existsByUsername(String username) {
        log.error("User service unavailable, existsByUsername: {}", username);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<Boolean> existsByEmail(String email) {
        log.error("User service unavailable, existsByEmail: {}", email);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }

    @Override
    public Result<Boolean> existsByPhone(String phone) {
        log.error("User service unavailable, existsByPhone: {}", phone);
        return Result.failed(ResultCode.SERVICE_UNAVAILABLE);
    }
}
