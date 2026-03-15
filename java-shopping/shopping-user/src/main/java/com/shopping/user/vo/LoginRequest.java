package com.shopping.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Login request")
public class LoginRequest {

    @NotBlank(message = "Username cannot be empty")
    @Schema(description = "Username", required = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Schema(description = "Password", required = true)
    private String password;

    @Schema(description = "Captcha code")
    private String captcha;

    @Schema(description = "Captcha key")
    private String captchaKey;
}
