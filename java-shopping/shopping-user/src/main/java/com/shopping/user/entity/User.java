package com.shopping.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
@Schema(description = "User entity")
public class User extends BaseEntity {

    @Schema(description = "Username")
    private String username;

    @Schema(description = "Password (encrypted)")
    private String password;

    @Schema(description = "Nickname")
    private String nickname;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Phone")
    private String phone;

    @Schema(description = "Avatar URL")
    private String avatar;

    @Schema(description = "Gender (0: unknown, 1: male, 2: female)")
    private Integer gender;

    @Schema(description = "Birthday")
    private String birthday;

    @Schema(description = "User status (0: normal, 1: locked, 2: disabled)")
    private Integer status;

    @Schema(description = "Last login time")
    private String lastLoginTime;

    @Schema(description = "Last login IP")
    private String lastLoginIp;

    @Schema(description = "Login count")
    private Integer loginCount;

    @Schema(description = "Remark")
    private String remark;
}
