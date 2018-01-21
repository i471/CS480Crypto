package com.example.robertgil.cs480crypto;

/**
 * Created by Alec on 1/20/2018.
 */

public class User {

    private String email;
    private String walletId;

    User(String email, String walletId) {
        this.email = email;
        this.walletId = walletId;
    }

    public String getEmail() {
        return email;
    }

    public String getWalletId() {
        return walletId;
    }
}
