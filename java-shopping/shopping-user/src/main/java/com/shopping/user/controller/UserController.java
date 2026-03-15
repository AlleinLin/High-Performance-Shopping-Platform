package com.shopping.user.controller;

import com.shopping.api.dto.UserDTO;
import com.shopping.common.annotation.RateLimit;
import com.shopping.common.result.PageResult;
import com.shopping.common.result.Result;
import com.shopping.user.service.UserService;
import com.shopping.user.vo.LoginRequest;
import com.shopping.user.vo.LoginResponse;
import com.shopping.user.vo.RegisterRequest;
import com.shopping.user.vo.UserUpdateRequest;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User API", description = "User management and authentication APIs")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "User login", description = "Authenticate user and return token")
    @PostMapping("/login")
    @RateLimit(key = "login", limit = 10, period = 60)
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return Result.success(userService.login(request));
    }

    @Operation(summary = "User registration", description = "Register a new user account")
    @PostMapping("/register")
    @RateLimit(key = "register", limit = 5, period = 60)
    public Result<LoginResponse> register(@Valid @RequestBody RegisterRequest request) {
        return Result.success(userService.register(request));
    }

    @Operation(summary = "Refresh token", description = "Refresh access token using refresh token")
    @PostMapping("/refresh")
    public Result<LoginResponse> refreshToken(@RequestHeader("Refresh-Token") String refreshToken) {
        return Result.success(userService.refreshToken(refreshToken));
    }

    @Operation(summary = "User logout", description = "Logout and invalidate token")
    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        if (authorization != null && authorization.startsWith("Bearer ")) {
            userService.logout(authorization.substring(7));
        }
        return Result.success();
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user information by user ID")
    @GetMapping("/{id}")
    public Result<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return user != null ? Result.success(user) : Result.failed("User not found");
    }

    @Operation(summary = "Get user by username", description = "Retrieve user information by username")
    @GetMapping("/username/{username}")
    public Result<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        return user != null ? Result.success(user) : Result.failed("User not found");
    }

    @Operation(summary = "Get user list", description = "Retrieve paginated user list")
    @GetMapping("/list")
    public Result<PageResult<UserDTO>> getUserList(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "Search keyword") @RequestParam(required = false) String keyword) {
        return Result.success(userService.getUserList(pageNum, pageSize, keyword));
    }

    @Operation(summary = "Update user", description = "Update user profile")
    @PutMapping("/{id}")
    public Result<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return Result.success(userService.updateUser(id, request));
    }

    @Operation(summary = "Update user status", description = "Update user account status")
    @PutMapping("/{id}/status")
    public Result<Boolean> updateStatus(
            @PathVariable Long id,
            @Parameter(description = "Status (0: normal, 1: locked, 2: disabled)") @RequestParam Integer status) {
        return Result.success(userService.updateStatus(id, status));
    }

    @Operation(summary = "Update password", description = "Update user password")
    @PutMapping("/{id}/password")
    public Result<Boolean> updatePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        return Result.success(userService.updatePassword(id, oldPassword, newPassword));
    }

    @Operation(summary = "Reset password", description = "Reset user password (admin operation)")
    @PutMapping("/{id}/password/reset")
    public Result<Boolean> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        return Result.success(userService.resetPassword(id, newPassword));
    }

    @Operation(summary = "Check username exists", description = "Check if username is already registered")
    @GetMapping("/exists")
    public Result<Boolean> existsByUsername(@RequestParam String username) {
        return Result.success(userService.existsByUsername(username));
    }

    @Operation(summary = "Check email exists", description = "Check if email is already registered")
    @GetMapping("/email/exists")
    public Result<Boolean> existsByEmail(@RequestParam String email) {
        return Result.success(userService.existsByEmail(email));
    }

    @Operation(summary = "Check phone exists", description = "Check if phone is already registered")
    @GetMapping("/phone/exists")
    public Result<Boolean> existsByPhone(@RequestParam String phone) {
        return Result.success(userService.existsByPhone(phone));
    }
}
