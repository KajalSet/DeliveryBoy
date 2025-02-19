package com.solwyz.deliveryBoy.pojo.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
    private String mpin;

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getMpin() { return mpin; }
    public void setMpin(String mpin) { this.mpin = mpin; }
}





