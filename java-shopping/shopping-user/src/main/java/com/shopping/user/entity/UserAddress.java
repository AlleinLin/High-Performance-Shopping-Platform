package com.shopping.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.shopping.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_address")
@Schema(description = "User address entity")
public class UserAddress extends BaseEntity {

    @Schema(description = "User ID")
    private Long userId;

    @Schema(description = "Receiver name")
    private String receiverName;

    @Schema(description = "Receiver phone")
    private String receiverPhone;

    @Schema(description = "Province code")
    private String provinceCode;

    @Schema(description = "Province name")
    private String provinceName;

    @Schema(description = "City code")
    private String cityCode;

    @Schema(description = "City name")
    private String cityName;

    @Schema(description = "District code")
    private String districtCode;

    @Schema(description = "District name")
    private String districtName;

    @Schema(description = "Detailed address")
    private String detailAddress;

    @Schema(description = "Postal code")
    private String postalCode;

    @Schema(description = "Is default address")
    private Boolean isDefault;

    @Schema(description = "Address tag (home, company, etc.)")
    private String tag;
}
