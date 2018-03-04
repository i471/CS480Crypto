package com.example.robertgil.cs480crypto;

import java.io.Serializable;

/**
 * Created by Alec on 1/20/2018.
 */

public class User implements Serializable {

    private String email;
    private String walletId;
    private String apiKey;
    private String secretKey;

    User() {
        email = null;
        walletId = null;
    }

    User(String email, String walletId) {
        this.email = email;
        this.walletId = walletId;
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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
