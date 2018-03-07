package com.example.robertgil.cs480crypto;

import org.json.JSONException;

import java.io.IOException;


class WalletController {
    private final WalletModel model;
    private final WalletView view;
    private String api_Key;
    private String recipient_Address;
    private Double amount;
    private String secret_Key;

    WalletController(WalletView view, WalletModel model) {
        this.view = view;
        this.model = model;
    }

    public void accessAccountBalance(String APIKEY) throws IOException, JSONException {
        String sb = "https://block.io/api/v2/get_balance/?api_key=";
        sb += APIKEY;
        model.setJsonResponse(model.getJSON(sb));
    }

    public void withDrawFromAccount(String APIKEY, Double amount_to_send, String adress_to_send_to, String secret_pin) throws IOException, JSONException {
        String sb = "https://block.io/api/v2/withdraw/?api_key=" + APIKEY + "&amounts=" + amount_to_send + "&to_addresses=" + adress_to_send_to + "&pin=" + secret_pin;
        model.setJsonResponse(model.getJSON(sb));
    }

    public void estimateNetworkFee(String APIKey, Double amount_to_send, String adress_to_send_to) throws IOException, JSONException {
        String sb = "https://block.io/api/v2/get_network_fee_estimate/?api_key=" + APIKey + "&amounts=" + amount_to_send + "&to_addresses=" + adress_to_send_to;
        model.setJsonResponse(model.getJSON(sb));
    }


    public void setRecipent_Adress(String recipient_Address) {
        this.recipient_Address = recipient_Address;
    }

    public void setAPIkey(String api_Key) {
        this.api_Key = api_Key;
    }


    public void setSecret_Key(String secret_Key) {
        this.secret_Key = secret_Key;
    }

    public String getApi_Key() {
        return api_Key;
    }

    public String getrecipient_Address() {
        return recipient_Address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSecret_Key() {
        return secret_Key;
    }


}
