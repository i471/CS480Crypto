package com.example.robertgil.cs480crypto;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;


public class SendAndReceiveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    TextView tv;
    EditText amountEditText;
    private static final String[] paths = {"Dogecoin", "Bitcoin", "Litecoin"};
    WalletModel model = new WalletModel();
    WalletView view = new WalletView();
    WalletController testRun = new WalletController(view, model);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_send_and_receive);

        //Spinner SetUp
        spinner = findViewById(R.id.walletSpinnerID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        tv = findViewById(R.id.adressToSendToText);

        // NEED TO SET USERS API KEY BEFORE WALLET API WORKS
        testRun.setAPIkey("e5ed-0847-8256-7f49"); // Need Api key for account access

        //SET SERVICE FEE AT BOTTOM OF PAGE
        //Edittext field of the amount, keeps a live view of the wallet service fee
        amountEditText = findViewById(R.id.amountEditText);
        amountEditText.setFocusableInTouchMode(true);
        amountEditText.requestFocus();

        amountEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    new MyTask().execute(amountEditText.getText().toString());
                    return true;
                }
                return false;
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
                Toast.makeText(this, "dogeselected", Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.dogecoin);
                tv.setText("DOGE");
                break;
            case 1:
                Toast.makeText(this, "bitcoinselected", Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.bitcoin);
                tv.setText("BTC");
                break;
            case 2:
                Toast.makeText(this, "litcoinselected", Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.litecoin2);
                tv.setText("LTC");
                break;

        }
    }

    /**
     * onNothingSelected for the send and recieve spinner.
     * Used when nothing on the spinner is selected
     */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "NOTHINGSELECTED", Toast.LENGTH_SHORT).show();

    }


    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            //accessAccountBalance(strings[0]);
            tv.setText(strings[0]);
            return null;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
        }


    }


}
