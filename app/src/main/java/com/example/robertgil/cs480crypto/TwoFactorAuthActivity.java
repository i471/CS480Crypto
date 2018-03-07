package com.example.robertgil.cs480crypto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

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
        final CheckBox checkBox = (CheckBox) findViewById(R.id.trustDevice);
        final Bundle b = getIntent().getExtras();
        final User user = (User) b.getSerializable("user");
        final String email = user.getEmail();
        try {
            twoFactorAuth(email);
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
                    if (checkBox.isChecked()) {
                        Log.d(TAG, "The codes are a match");
                        final Context context = getApplicationContext();
                        final String filename = "trusted.cfg";
                        final File file = new File(context.getFilesDir(), filename);
                        try {
                            FileWriter fw = new FileWriter(file);
                            fw.write("trusted " + email);
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Bundle b = new Bundle();
                    b.putSerializable("user", user);
                    Intent intent = new Intent(TwoFactorAuthActivity.this, MainActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
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
        asker.close();
        Log.d(TAG, "Email code: " + code);
    }


    private class EmailAsker extends Thread {

        /* Replace this field with whatever your public IP is my dude */
        //private final String ipAddress = "47.148.246.200";
        private final String ipAddress = "47.148.246.200";
        private final int PORT = 25443;
        private String email;
        private ObjectInputStream ois;
        private String code;
        private Socket socket;

        private EmailAsker(){}

        EmailAsker(String email) {
            this.email = email;
        }

        @Override
        public void run() {
            try {
                socket = askForEmail();
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
            Socket socket = null;
            while (true) {
                try {
                    socket = new Socket(ipAddress, PORT);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    Thread.yield();
                }
            }
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(email);
            return socket;
        }

        public void close() throws IOException {
            socket.close();
        }

    }

}
