package com.example.robertgil.cs480crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.SecureRandom;

public class TwoFactorAuthActivity extends AppCompatActivity {

    private final String TAG = "TwoFactorAuthActivity";
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_factor_auth);
        Bundle b = getIntent().getExtras();
        try {
            twoFactorAuth(b.get("email").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void twoFactorAuth(final String email) throws IOException {
        EmailAsker asker = new EmailAsker(email);
        asker.start();
        while ((code = asker.getCode()) == null) {
            Thread.yield();
        }
        //TODO use code to check against user input for 2FA
        Log.d(TAG, "Email code: " + code);
    }


    private class EmailAsker extends Thread {

        private final String ipAddress = "134.71.136.130";
        private final int PORT = 25443;
        private String email;
        private ObjectInputStream ois;
        private String code;

        EmailAsker(){}

        EmailAsker(String email) {
            this.email = email;
        }

        @Override
        public void run() {
            try {
                Socket socket = askForEmail();
                ois = new ObjectInputStream(socket.getInputStream());
                while ((code = ois.readObject().toString()) == null) {
                    Thread.yield();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        public String getCode() {
            return code;
        }

        public Socket askForEmail() throws IOException {
            Socket socket = new Socket(ipAddress, PORT);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(email);
            return socket;
        }

    }

}
