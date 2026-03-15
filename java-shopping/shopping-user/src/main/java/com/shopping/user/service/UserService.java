package com.shopping.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shopping.api.dto.UserDTO;
import com.shopping.common.result.PageResult;
import com.shopping.user.entity.User;
import com.shopping.user.vo.LoginRequest;
import com.shopping.user.vo.LoginResponse;
import com.shopping.user.vo.RegisterRequest;
import com.shopping.user.vo.UserUpdateRequest;

public interface UserService extends IService<User> {

    LoginResponse login(LoginRequest request);

    LoginResponse register(RegisterRequest request);

    LoginResponse refreshToken(String refreshToken);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    PageResult<UserDTO> getUserList(Integer pageNum, Integer pageSize, String keyword);

    UserDTO updateUser(Long id, UserUpdateRequest request);

    Boolean updateStatus(Long id, Integer status);

    Boolean updatePassword(Long id, String oldPassword, String newPassword);

    Boolean resetPassword(Long id, String newPassword);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);

    void logout(String token);
}
