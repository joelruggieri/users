package com.cashonline.http.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("total")
    private Double total;

    public LoanResponse(Integer id, Integer userId, Double total) {
        this.id = id;
        this.userId = userId;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
