package com.shopping.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
@Schema(description = "Paginated result")
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Data list")
    private List<T> list;

    @Schema(description = "Total count")
    private Long total;

    @Schema(description = "Page number")
    private Integer pageNum;

    @Schema(description = "Page size")
    private Integer pageSize;

    @Schema(description = "Total pages")
    private Integer pages;

    @Schema(description = "Has next page")
    private Boolean hasNext;

    @Schema(description = "Has previous page")
    private Boolean hasPrevious;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
        this.hasNext = pageNum < this.pages;
        this.hasPrevious = pageNum > 1;
    }

    public static <T> PageResult<T> of(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        return new PageResult<>(list, total, pageNum, pageSize);
    }

    public static <T> PageResult<T> empty(Integer pageNum, Integer pageSize) {
        return new PageResult<>(Collections.emptyList(), 0L, pageNum, pageSize);
    }
}
