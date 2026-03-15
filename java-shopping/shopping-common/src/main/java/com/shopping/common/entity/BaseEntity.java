package com.shopping.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "Primary key ID (Snowflake ID)")
    private Long id;

    @Schema(description = "Creation time")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "Update time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "Creator ID")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @Schema(description = "Updater ID")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @Schema(description = "Deleted flag (0: not deleted, 1: deleted)")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @Version
    @Schema(description = "Version for optimistic locking")
    private Integer version;
}
