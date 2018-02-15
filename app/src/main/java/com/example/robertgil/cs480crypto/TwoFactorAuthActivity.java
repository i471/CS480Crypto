package com.example.robertgil.cs480crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TwoFactorAuthActivity extends AppCompatActivity {

    private final String TAG = "TwoFactorAuthActivity";
    private String code;
    private EditText mCodeView;
    private Button mContinueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_factor_auth);
        mCodeView = (EditText) findViewById(R.id.code);
        mContinueButton = (Button) findViewById(R.id.continueButton);
        Bundle b = getIntent().getExtras();
        try {
            twoFactorAuth(b.get("email").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Wait for code to arrive in case socket is being slow
                while (code == null) {
                    Thread.yield();
                }
                if (codeIsCorrect()) {
                    Log.d(TAG, "The codes are a match");
                    //TODO update UI for main screen or something i guess lmfaoooooooooo
                }
            }
        });
    }

    private boolean codeIsCorrect() {
        return mCodeView.getText().toString().equals(code);
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

        /* Replace this field with whatever your public IP is my dude */
        private final String ipAddress = "47.148.246.200";
        private final int PORT = 25443;
        private String email;
        private ObjectInputStream ois;
        private String code;

        private EmailAsker(){}

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
