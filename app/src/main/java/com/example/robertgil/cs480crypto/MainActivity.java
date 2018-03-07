package com.example.robertgil.cs480crypto;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setTitle("Main Menu");
        Bundle b = getIntent().getExtras();
        user = (User) b.getSerializable("user");
        final TextInputEditText apiKeyInput = findViewById(R.id.apiKeyInput);
        final TextInputEditText secretKeyInput = findViewById(R.id.secretKeyInput);
        apiKeyInput.setText((user.getApiKey() != null) ? user.getApiKey() : "");
        secretKeyInput.setText((user.getSecretKey() != null) ? user.getSecretKey() : "");

        final TextView loggedInAs = findViewById(R.id.loggedInAs);
        loggedInAs.setText("Logged in as\n" + user.getEmail());
        loggedInAs.setTypeface(null, Typeface.ITALIC);
        final Button saveButton = findViewById(R.id.saveApiInfo);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                user.setApiKey(apiKeyInput.getText().toString());
                user.setSecretKey(secretKeyInput.getText().toString());
                saveUserInfo();
            }
        });
    }

    private void saveUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/apiKey");
        ref.setValue(user.getApiKey());
        ref = FirebaseDatabase.getInstance().getReference("users/" + user.getUid() + "/secretKey");
        ref.setValue(user.getSecretKey());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater  = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Bundle b = new Bundle();
        b.putSerializable("user", user);
        switch(item.getItemId())
        {
            case R.id.about:
                Intent intent1 = new Intent(this, About.class);
                intent1.putExtras(b);
                this.startActivity(intent1);
                return true;
            case R.id.markets:
                Intent intent2 = new Intent(this, AnalyticsActivity.class);
                intent2.putExtras(b);
                this.startActivity(intent2);
                return true;
            case R.id.settings:
                Intent intent3 = new Intent(this, SettingsActivity.class);
                intent3.putExtras(b);
                this.startActivity(intent3);
                return true;
            case R.id.send:
                Intent intent4 = new Intent(this, SendAndReceiveActivity.class);
                intent4.putExtras(b);
                this.startActivity(intent4);
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
