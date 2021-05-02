package com.cashonline.http.input;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;


public class CreateUserInput {

    @JsonProperty("first_name")
    @NotNull
    private String firstName;

    @JsonProperty("last_name")
    @NotNull
    private String lastName;

    @JsonProperty("email")
    @NotNull
    private String email;

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
}
