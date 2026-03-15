package com.shopping.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

@Data
@Schema(description = "Unified API Response")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Response code")
    private Integer code;

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Response data")
    private T data;

    @Schema(description = "Timestamp")
    private Long timestamp;

    @Schema(description = "Trace ID for distributed tracing")
    private String traceId;

    public Result() {
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed() {
        return failed(ResultCode.FAILED);
    }

    public static <T> Result<T> failed(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FAILED.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> failed(ResultCode resultCode) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        return result;
    }

    public static <T> Result<T> failed(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> validateFailed(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.VALIDATE_FAILED.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> unauthorized(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.UNAUTHORIZED.getCode());
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> forbidden(String message) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.FORBIDDEN.getCode());
        result.setMessage(message);
        return result;
    }

    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }

    public Result<T> traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }
}
