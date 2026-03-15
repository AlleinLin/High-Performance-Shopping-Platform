package com.shopping.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Register request")
public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$", message = "Username must start with a letter and contain only letters, numbers and underscores")
    @Schema(description = "Username", required = true)
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    @Schema(description = "Password", required = true)
    private String password;

    @NotBlank(message = "Confirm password cannot be empty")
    @Schema(description = "Confirm password", required = true)
    private String confirmPassword;

    @Email(message = "Invalid email format")
    @Schema(description = "Email")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "Invalid phone number format")
    @Schema(description = "Phone")
    private String phone;

    @Schema(description = "Verification code")
    private String verifyCode;

    @Schema(description = "Verification code key")
    private String verifyCodeKey;
}
