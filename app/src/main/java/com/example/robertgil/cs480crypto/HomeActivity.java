package com.example.robertgil.cs480crypto;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        showProgress(true);
        Bundle b = getIntent().getExtras();
        getUserData(b);
    }

    private void getUserData(Bundle b) {
        String uid = b.get("uid").toString();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + uid);
        final User user = new User();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String key = dataSnapshot.getKey();
                final String value = dataSnapshot.getValue().toString();
                if (key.equals("email")) {
                    user.setEmail(value);
                } else if (key.equals("walletId")) {
                    user.setWalletId(value);
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Log.d(TAG, key);
                //TODO update UI here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showProgress(boolean show) {

    }

}
