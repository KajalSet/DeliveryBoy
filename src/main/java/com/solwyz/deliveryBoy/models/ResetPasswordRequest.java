package com.solwyz.deliveryBoy.models;

public class ResetPasswordRequest {
    private String username;
    private String mpin;
    private String newPassword;

    // Constructors
    public ResetPasswordRequest() {}

    public ResetPasswordRequest(String username, String mpin, String newPassword) {
        this.username = username;
        this.mpin = mpin;
        this.newPassword = newPassword;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMpin() {
        return mpin;
    }

    public void setMpin(String mpin) {
        this.mpin = mpin;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
