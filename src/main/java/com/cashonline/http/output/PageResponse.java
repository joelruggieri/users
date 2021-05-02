package com.cashonline.http.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageResponse {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("total")
    private Integer total;

    public PageResponse(Integer page, Integer size, Integer total) {
        this.page = page;
        this.size = size;
        this.total = total;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }
}
