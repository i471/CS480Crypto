package com.example.robertgil.cs480crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.katepratik.msg91api.MSG91;

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
        twoFactorAuth(phone);
    }

    private void twoFactorAuth(final String phone)  {
        final String AUTH_KEY = "197390AB3Kzpivb4KJ5a7d1926";
        MSG91 msg91 = new MSG91(AUTH_KEY);
        String validate = msg91.validate();
        msg91.composeMessage("testID","BIG TEST MESSAGE");
        msg91.to(phone);
        String result = msg91.send();
        Log.d(TAG, "Send result: " + result);
    }

    private String generateCode() {
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) rand.nextInt(57) + 65);
        }
        return sb.toString();
    }

}
