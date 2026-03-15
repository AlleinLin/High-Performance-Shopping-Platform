package com.shopping.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopping.api.dto.UserDTO;
import com.shopping.common.constant.RedisConstants;
import com.shopping.common.enums.UserStatus;
import com.shopping.common.exception.BusinessException;
import com.shopping.common.result.PageResult;
import com.shopping.common.result.ResultCode;
import com.shopping.common.util.JwtTokenProvider;
import com.shopping.user.entity.User;
import com.shopping.user.mapper.UserMapper;
import com.shopping.user.service.UserService;
import com.shopping.user.vo.LoginRequest;
import com.shopping.user.vo.LoginResponse;
import com.shopping.user.vo.RegisterRequest;
import com.shopping.user.vo.UserUpdateRequest;
import com.shopping.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = this.lambdaQuery()
                .eq(User::getUsername, request.getUsername())
                .one();

        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        if (!BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }

        if (UserStatus.LOCKED.getCode().equals(user.getStatus())) {
            throw new BusinessException(ResultCode.ACCOUNT_LOCKED);
        }

        if (UserStatus.DISABLED.getCode().equals(user.getStatus())) {
            throw new BusinessException(ResultCode.ACCOUNT_DISABLED);
        }

        String accessToken = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), "USER");
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId(), user.getUsername());

        redisTemplate.opsForValue().set(
                RedisConstants.USER_TOKEN_PREFIX + user.getId(),
                accessToken,
                RedisConstants.TOKEN_EXPIRE_TIME,
                TimeUnit.SECONDS
        );

        this.lambdaUpdate()
                .eq(User::getId, user.getId())
                .set(User::getLastLoginTime, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .set(User::getLoginCount, user.getLoginCount() + 1)
                .update();

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);
        response.setExpiresIn(86400L);
        response.setUser(BeanUtil.copyProperties(user, UserVO.class));

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException("Passwords do not match");
        }

        if (existsByUsername(request.getUsername())) {
            throw new BusinessException(ResultCode.USER_EXISTS);
        }

        if (StringUtils.hasText(request.getEmail()) && existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        if (StringUtils.hasText(request.getPhone()) && existsByPhone(request.getPhone())) {
            throw new BusinessException("Phone already registered");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(BCrypt.hashpw(request.getPassword()));
        user.setNickname(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setStatus(UserStatus.NORMAL.getCode());
        user.setGender(0);
        user.setLoginCount(0);

        this.save(user);

        return login(new LoginRequest() {{
            setUsername(request.getUsername());
            setPassword(request.getPassword());
        }});
    }

    @Override
    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException("Invalid refresh token");
        }

        Long userId = jwtTokenProvider.getUserId(refreshToken);
        String username = jwtTokenProvider.getUsername(refreshToken);

        User user = this.getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        String accessToken = jwtTokenProvider.generateToken(userId, username, "USER");
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(userId, username);

        redisTemplate.opsForValue().set(
                RedisConstants.USER_TOKEN_PREFIX + userId,
                accessToken,
                RedisConstants.TOKEN_EXPIRE_TIME,
                TimeUnit.SECONDS
        );

        LoginResponse response = new LoginResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(newRefreshToken);
        response.setExpiresIn(86400L);
        response.setUser(BeanUtil.copyProperties(user, UserVO.class));

        return response;
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = this.getById(id);
        if (user == null) {
            return null;
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = this.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
        if (user == null) {
            return null;
        }
        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public PageResult<UserDTO> getUserList(Integer pageNum, Integer pageSize, String keyword) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(User::getUsername, keyword)
                    .or().like(User::getNickname, keyword)
                    .or().like(User::getEmail, keyword)
                    .or().like(User::getPhone, keyword);
        }
        wrapper.orderByDesc(User::getCreateTime);

        Page<User> page = this.page(new Page<>(pageNum, pageSize), wrapper);

        return PageResult.of(
                page.getRecords().stream()
                        .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                        .toList(),
                page.getTotal(),
                pageNum,
                pageSize
        );
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateRequest request) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        if (StringUtils.hasText(request.getNickname())) {
            user.setNickname(request.getNickname());
        }
        if (StringUtils.hasText(request.getAvatar())) {
            user.setAvatar(request.getAvatar());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());
        }
        if (StringUtils.hasText(request.getBirthday())) {
            user.setBirthday(request.getBirthday());
        }
        if (StringUtils.hasText(request.getEmail())) {
            if (!request.getEmail().equals(user.getEmail()) && existsByEmail(request.getEmail())) {
                throw new BusinessException("Email already registered");
            }
            user.setEmail(request.getEmail());
        }
        if (StringUtils.hasText(request.getPhone())) {
            if (!request.getPhone().equals(user.getPhone()) && existsByPhone(request.getPhone())) {
                throw new BusinessException("Phone already registered");
            }
            user.setPhone(request.getPhone());
        }

        this.updateById(user);

        return BeanUtil.copyProperties(user, UserDTO.class);
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        user.setStatus(status);
        return this.updateById(user);
    }

    @Override
    public Boolean updatePassword(Long id, String oldPassword, String newPassword) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }

        user.setPassword(BCrypt.hashpw(newPassword));
        return this.updateById(user);
    }

    @Override
    public Boolean resetPassword(Long id, String newPassword) {
        User user = this.getById(id);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        user.setPassword(BCrypt.hashpw(newPassword));
        return this.updateById(user);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .exists();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.lambdaQuery()
                .eq(User::getEmail, email)
                .exists();
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return this.lambdaQuery()
                .eq(User::getPhone, phone)
                .exists();
    }

    @Override
    public void logout(String token) {
        try {
            Long userId = jwtTokenProvider.getUserId(token);
            redisTemplate.delete(RedisConstants.USER_TOKEN_PREFIX + userId);
        } catch (Exception e) {
            log.warn("Logout failed: {}", e.getMessage());
        }
    }
}
