package com.example.robertgil.cs480crypto;

import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;

        import java.net.URL;
/*
 * This class is just used to get an open connection to the web and make URL calls according to Block.io's API per methods
 */

class WalletModel {

    private JSONObject jsonResponse;

    public JSONObject getJSON(String url) throws IOException, JSONException {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return jsonResponse = new JSONObject(response.toString());
    }

    public JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public void setJsonResponse(JSONObject jsonResponse) {
        this.jsonResponse = jsonResponse;
    }
}
