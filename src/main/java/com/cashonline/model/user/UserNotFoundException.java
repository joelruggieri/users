package com.cashonline.model.user;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(Integer userId) {
        super("User with id " + userId + "not found.");
    }
}
