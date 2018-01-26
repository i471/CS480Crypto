package com.example.robertgil.cs480crypto;

/**
 * Created by Alec on 1/20/2018.
 */

public class User {

    private String email;
    private String phone;
    private String walletId;

    User() {
        email = null;
        walletId = null;
        phone = null;
    }

    // User does not have 2FA
    User(String email, String walletId) {
        this.email = email;
        this.walletId = walletId;
        this.phone = null;
    }

    // User has 2FA
    User(String email, String phone, String walletId) {
        this.email = email;
        this.phone = phone;
        this.walletId = walletId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getEmail() {
        return email;
    }

    public String getWalletId() {
        return walletId;
    }
}
