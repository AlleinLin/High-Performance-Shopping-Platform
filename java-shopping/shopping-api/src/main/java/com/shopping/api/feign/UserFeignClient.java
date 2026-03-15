package com.shopping.api.feign;

import com.shopping.api.dto.UserDTO;
import com.shopping.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "shopping-user", contextId = "userFeignClient")
public interface UserFeignClient {

    @GetMapping("/api/user/{id}")
    Result<UserDTO> getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/user/username/{username}")
    Result<UserDTO> getUserByUsername(@PathVariable("username") String username);

    @GetMapping("/api/user/exists")
    Result<Boolean> existsByUsername(@RequestParam("username") String username);

    @GetMapping("/api/user/email/exists")
    Result<Boolean> existsByEmail(@RequestParam("email") String email);

    @GetMapping("/api/user/phone/exists")
    Result<Boolean> existsByPhone(@RequestParam("phone") String phone);
}
