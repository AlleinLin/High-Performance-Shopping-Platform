package com.shopping.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Login response")
public class LoginResponse {

    @Schema(description = "Access token")
    private String accessToken;

    @Schema(description = "Refresh token")
    private String refreshToken;

    @Schema(description = "Token type")
    private String tokenType = "Bearer";

    @Schema(description = "Expires in (seconds)")
    private Long expiresIn;

    @Schema(description = "User info")
    private UserVO user;
}
