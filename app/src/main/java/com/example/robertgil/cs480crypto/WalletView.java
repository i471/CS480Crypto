package com.example.robertgil.cs480crypto;
import org.json.JSONException;
import org.json.JSONObject;

/**
All the visual calls for the console prototype, returns objects to parse from and can extract
specific types of data depending on the call.
* */

public class WalletView {

    public void getNetwork(JSONObject Response) throws JSONException {
        //System.out.println("Network : " + Response.getJSONObject("data").getString("network"));
    }

    public String getBalance(JSONObject Response) throws JSONException {
        //System.out.println("Balance : " + Response.getJSONObject("data").getString("available_balance"));
        return Response.getJSONObject("data").getString("available_balance");
    }

    public void get_Amount_Sent(JSONObject Response) throws JSONException {
        //System.out.println("Amount Sent : " + Response.getJSONObject("data").getString("amount_sent"));

    }

    public String get_Network_Fee(JSONObject Response) throws JSONException {
        //System.out.println("Network Fee : " + Response.getJSONObject("data").getString("network_fee"));
        return Response.getJSONObject("data").getString("network_fee");

    }
    public String get_estimated_network_fee(JSONObject Response) throws JSONException {
        System.out.println("Estimated Network Fee : " + Response.getJSONObject("data").getString("estimated_network_fee"));
        return Response.getJSONObject("data").getString("estimated_network_fee");

    }


    public void getStatus(JSONObject Response) throws JSONException {
        System.out.println("Status : " + Response.getString("status"));
    }

    public void printAccountDetails(JSONObject Response) throws JSONException {
        System.out.println("Status : " + Response.getString("status"));
        System.out.println("Network : " + Response.getJSONObject("data").getString("network"));
        System.out.println("Balance : " + Response.getJSONObject("data").getString("available_balance"));
    }

    public void obtain_Key_String() {
        System.out.println("Please Enter API Key");
    }

    public void obtain_Secret_Key() {
        System.out.println("Please Enter your Secret_Pin to withdraw funds");
    }

    public void obtain_Recipient_Address() {
        System.out.println("Where would you like to send to? (Input address on network)");
    }

    public void obtain_Amount_To_Send() {
        System.out.println("How much would you like to send?");
    }

    public void IncorrectOption() {
        System.out.println("Incorrect option, please try again.");
    }

    public void pickOptionString() {
        System.out.println("" +
                "\n1. Check Account Balance." +
                "\n2. Check Account Type" +
                "\n3. Send Currency to an Address" +
                "\n4. Print Account Details" +
                "\n5. Exit" +
                "\n6. Display Options"+
                "\n7. Estimate Network Fee"
        );

    }



}
