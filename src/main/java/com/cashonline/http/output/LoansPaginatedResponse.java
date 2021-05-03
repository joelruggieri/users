package com.cashonline.http.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class LoansPaginatedResponse {
    @JsonProperty("items")
    private List<LoanResponse> loans;

    @JsonProperty("paging")
    private PageResponse paging;

    public LoansPaginatedResponse(List<LoanResponse> items, PageResponse paging) {
        this.loans = items;
        this.paging = paging;
    }

    public void setLoans(List<LoanResponse> loans) {
        this.loans = loans;
    }

    public List<LoanResponse> getLoans() {
        return loans;
    }

    public void setPaging(PageResponse paging) {
        this.paging = paging;
    }

    public PageResponse getPaging() {
        return paging;
    }
}
