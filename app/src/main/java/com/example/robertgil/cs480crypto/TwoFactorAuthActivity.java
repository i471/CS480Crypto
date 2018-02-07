package com.example.robertgil.cs480crypto;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.TextMessage;

import java.io.IOException;
import java.security.SecureRandom;

public class TwoFactorAuthActivity extends AppCompatActivity {

    private final String TAG = "TwoFactorAuthActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_factor_auth);
        Bundle b = getIntent().getExtras();
        String phone = b.getString("phone");
        Log.d(TAG,"Phone number: " + phone);
        try {
            twoFactorAuth(phone);
        } catch (IOException | NexmoClientException e) {
            e.printStackTrace();
        }
    }

    private void twoFactorAuth(final String phone) throws IOException, NexmoClientException {
            NexmoClient client = createNexmoClient();
            sendMessage(client, phone);
    }

    private void sendMessage(NexmoClient client, final String phone) throws IOException, NexmoClientException {
        final String NEXMO_NUMBER = "12014160210";
        TextMessage message = new TextMessage(NEXMO_NUMBER, phone, generateCode());
        SmsSubmissionResult[] responses = client.getSmsClient().submitMessage(message);
    }

    private String generateCode() {
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) rand.nextInt(57) + 65);
        }
        return sb.toString();
    }

    private NexmoClient createNexmoClient() {
        Context context = getApplicationContext();
        // Establish Nexmo data
        final String API_KEY = "4aa63181";
        final String API_SECRET = "ac18cc7f5f270daa";
        AuthMethod auth = new TokenAuthMethod(API_KEY, API_SECRET);
        NexmoClient client = new NexmoClient(auth);
        return client;
    }
}
