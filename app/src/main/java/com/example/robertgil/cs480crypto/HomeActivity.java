package com.example.robertgil.cs480crypto;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends BaseActivity {

    private final User user = new User();

    private final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle b = getIntent().getExtras();
        getUserData(b);
    }

    private void getUserData(Bundle b) {
        String uid = b.get("uid").toString();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    String key = childSnap.getKey();
                    String value = childSnap.getValue().toString();
                    if (key.equals("email")) {
                        user.setEmail(value);
                    } else if (key.equals("walletId")) {
                        user.setWalletId(value);
                    }
                }
                Log.d(TAG, user.getEmail());
                updateUI();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI() {
        // Remove loading view
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        // Load user information
        final TextView loggedinAs = findViewById(R.id.loggedInAs);
        loggedinAs.setText("Logged in as " + user.getEmail());
        //TODO get wallet balance, put it in UI
        double balance = 2.334563;
        final TextView balanceView = findViewById(R.id.balanceView);
        balanceView.setText("Current balance: " + balance);
        findViewById(R.id.infoLayout).setVisibility(View.VISIBLE);
    }

}
