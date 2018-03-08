package com.example.robertgil.cs480crypto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;


public class SendAndReceiveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private TextView serviceFeeID, balanceID;
    private EditText addressToSendToID, amountEditText;
    private Button sendBtn;
    private User user;
    private WalletModel model = new WalletModel();
    private WalletView view = new WalletView();
    private WalletController testRun = new WalletController(view, model);
    private static final String[] paths = {"Dogecoin", "Bitcoin", "Litecoin"};
    private String apiKey, secretKey, walletID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_send_and_receive);

        Bundle b = getIntent().getExtras();
        user = (User) b.getSerializable("user");


        initWidgets();
        initFirebaseVar();
        setButtonListeners();

    }

    private void initFirebaseVar() {
        apiKey = user.getApiKey();
        secretKey = user.getSecretKey();
        testRun.setAPIkey(apiKey); // Need Api key for account access
        testRun.setSecret_Key(secretKey); // API also requires Secret key
    }



    private void setButtonListeners() {

        /**
         * Receives input from the ADDRESS EditText Field
         * Used to get input from user about which adress to send the coin too
         * as soon as the accept button or enter is pressed its saved into the instance
         * */

        addressToSendToID.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Toast.makeText(SendAndReceiveActivity.this, "Address Received", Toast.LENGTH_SHORT).show();
                    testRun.setRecipent_Adress(String.valueOf(addressToSendToID.getText()));
                    return true;
                }
                return false;
            }
        });

        /**
         * Receives input from the AMOUNT EditText Field
         * Used to get input from user about the amount of coin to send.
         * as soon as the accept button or enter is pressed its saved into the instance
         * */

        amountEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    String test;
                    test = String.valueOf(amountEditText.getText());
                    double number = Double.valueOf(test);
                    testRun.setAmount(number);

                    Toast.makeText(SendAndReceiveActivity.this, "Amount Received" + number + "hey", Toast.LENGTH_SHORT).show();
                    new getServiceFee().execute();
                    return true;
                }
                return false;
            }
        });

    }

    public void initWidgets() {
        amountEditText = findViewById(R.id.amountEditText);
        addressToSendToID = findViewById(R.id.adressToSendToText);
        serviceFeeID = findViewById(R.id.serviceFeeAmountID);
        balanceID = findViewById(R.id.BalanceAmountTextView);
        sendBtn = findViewById(R.id.sendButtonID);

        //**********Spinner Set Up***************
        spinner = findViewById(R.id.walletSpinnerID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //*********Button Initialization***********
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }

    public void accessAccountBalance(String APIKEY) throws IOException, JSONException {
        String sb = "https://block.io/api/v2/get_balance/?api_key=";
        sb += APIKEY;
        model.setJsonResponse(model.getJSON(sb));
    }

    /**
     * onItemSelected for the send and recieve spinner.
     * Used to change the picture and the amount text when a particular option is chosen on the wallet spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
        ImageView iv;
        TextView tv;

        iv = findViewById(R.id.typeOfWalletImage); // initialize walletpic to starting picture
        tv = findViewById(R.id.amountTextType);


        switch (pos) {
            case 0:
                testRun.setAPIkey("39c9-9dab-8d2a-62ff");
                Toast.makeText(this, testRun.getApi_Key(), Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.dogecoin);
                tv.setText("DOGE");
                new accessAccountBalance().execute(testRun.getApi_Key());

                break;
            case 1:
                testRun.setAPIkey("cc80-06be-af22-b8dd"); //DOGE WALLET
                Toast.makeText(this, testRun.getApi_Key(), Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.bitcoin);
                tv.setText("BTC");
                new accessAccountBalance().execute(testRun.getApi_Key());

                break;
            case 2:
                testRun.setAPIkey("5d56-aa37-16ff-d08b");//litecoinwallet
                Toast.makeText(this, testRun.getApi_Key(), Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.litecoin2);
                tv.setText("LTC");
                new accessAccountBalance().execute(testRun.getApi_Key());

                break;
        }
    }

    /**
     * onNothingSelected for the send and recieve spinner.
     * wont be using but need to implement anyway.
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private class accessAccountBalance extends AsyncTask<String, String, String> {
        //DecimalFormat format = new DecimalFormat("0.###");
        @Override
        protected String doInBackground(String... strings) {
            try {
                accessAccountBalance(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                String s;
                s = view.getBalance(model.getJsonResponse());
                s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
                balanceID.setText(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Confirmation Diaglog box to confirm the sending of the cryptocurrency
     * This prevents accidental sends on the app.
     * Provides two options on the alert dialog, yes/no and runs the code accordingly
     */
    private void confirmDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SendAndReceiveActivity.this);
        builder.setMessage("hi");
        builder.setMessage("Are you sure? A " + serviceFeeID.getText() + " network fee will be deducted from your account")
                .setPositiveButton("Yes, im sure.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(check())
                        new sendConfirmationOnButtonPress().execute();
                    }
                }).setNegativeButton("Nope nope nope", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(SendAndReceiveActivity.this, "", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        }).show();
    }


    private class getServiceFee extends AsyncTask<Void, Void, Void> {

        String apikey, recAddress;
        Double amount;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            apikey = testRun.getApi_Key();
            amount = testRun.getAmount();
            recAddress = testRun.getrecipient_Address();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                testRun.estimateNetworkFee(apikey, amount, recAddress);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(SendAndReceiveActivity.this, "Estimated", Toast.LENGTH_SHORT).show();
            try {
                serviceFeeID.setText(view.get_estimated_network_fee(model.getJsonResponse()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    
    public boolean check() {
        if (TextUtils.isEmpty(addressToSendToID.getText().toString())| TextUtils.isEmpty(user.getSecretKey()) | TextUtils.isEmpty(user.getApiKey())){
            System.out.println("FALSED");
            return false;
        }
        System.out.println("TRUE");
        return true;
    }

    /**
     * sendConfirmationOnButtonPress
     * This runs the api command to send the crypocurrency to the specified adress&amount.
     */
    private class sendConfirmationOnButtonPress extends AsyncTask<Void, Void, Void> {
        String apikey, recAddress, secretKey;
        Double amount;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            apikey = testRun.getApi_Key();
            amount = testRun.getAmount();
            recAddress = testRun.getrecipient_Address();
            secretKey = testRun.getSecret_Key();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                testRun.withDrawFromAccount(apikey, amount, recAddress, secretKey);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(SendAndReceiveActivity.this, "SENT", Toast.LENGTH_SHORT).show();

            new accessAccountBalance().execute(testRun.getApi_Key());

        }


    }

}





