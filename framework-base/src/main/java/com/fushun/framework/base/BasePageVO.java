package com.fushun.framework.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author Lanny
 * @since 2021/3/30
 */
@Data
public class BasePageVO <T> {
    @Schema($schema = "当前页")
    private Integer currentPage = 1;

    @Schema($schema ="页大小")
    private Integer pageSize = 10;

    @Schema($schema ="记录总数")
    private Long total = 0L;

    @Schema($schema ="列表数据")
    private List<T> data;
}
