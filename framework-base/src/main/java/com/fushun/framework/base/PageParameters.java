package com.fushun.framework.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageParameters {

    private int currentPage;

    private int pageSize;

    public int offset() {
        return (currentPage - 1) * pageSize;
    }


    public PageParameters() {
        this.currentPage = 1;
        this.pageSize = 10;
    }

    @Override
    public String toString() {
        return "{currentPage=" + currentPage + ", pageSize=" + pageSize + "}";
    }
}
