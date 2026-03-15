package com.shopping.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "User DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "User ID")
    private Long id;

    @Schema(description = "Username")
    private String username;

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

    @Schema(description = "User status")
    private Integer status;
}
