package com.shopping.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "User update request")
public class UserUpdateRequest {

    @Schema(description = "Nickname")
    private String nickname;

    @Schema(description = "Avatar URL")
    private String avatar;

    @Schema(description = "Gender (0: unknown, 1: male, 2: female)")
    private Integer gender;

    @Schema(description = "Birthday")
    private String birthday;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Phone")
    private String phone;
}
