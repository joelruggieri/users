package com.cashonline.http.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LoansPaginatedResponse {
    @JsonProperty("items")
    private List<LoanResponse> loans;

    @JsonProperty("paging")
    private PageResponse page;

    public void setLoans(List<LoanResponse> loans) {
        this.loans = loans;
    }

    public List<LoanResponse> getLoans() {
        return loans;
    }

    public void setPage(PageResponse page) {
        this.page = page;
    }

    public PageResponse getPage() {
        return page;
    }
}
