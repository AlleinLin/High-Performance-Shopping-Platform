package com.shopping.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    
    private Integer code;
    private String message;
    private T data;
    private Long timestamp;
}
