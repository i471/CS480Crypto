package com.example.robertgil.cs480crypto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * All the visual calls for the console prototype, returns objects to parse from and can extract
 * specific types of data depending on the call.
 */
public class WalletView {


    public String getBalance(JSONObject Response) throws JSONException {
        System.out.println("Balance : " + Response.getJSONObject("data").getString("available_balance"));
        return Response.getJSONObject("data").getString("available_balance");
    }

    public String get_estimated_network_fee(JSONObject Response) throws JSONException {
        return Response.getJSONObject("data").getString("estimated_network_fee");
    }

    public void getNetwork(JSONObject Response) throws JSONException {
        //System.out.println("Network : " + Response.getJSONObject("data").getString("network"));
    }

    public void get_Amount_Sent(JSONObject Response) throws JSONException {
        //System.out.println("Amount Sent : " + Response.getJSONObject("data").getString("amount_sent"));
    }


    public void getStatus(JSONObject Response) throws JSONException {
        System.out.println("Status : " + Response.getString("status"));
    }


}
