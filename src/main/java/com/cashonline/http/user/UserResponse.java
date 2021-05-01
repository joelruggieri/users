package com.cashonline.http.user;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("loans")
    private List<LoanResponse> loans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LoanResponse> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanResponse> loans) {
        this.loans = loans;
    }
}
