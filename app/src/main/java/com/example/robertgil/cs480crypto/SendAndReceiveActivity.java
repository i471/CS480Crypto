package com.example.robertgil.cs480crypto;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SendAndReceiveActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private static final String[] paths = {"Dogecoin", "Bitcoin", "Litecoin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_send_and_receive);

        spinner = findViewById(R.id.walletSpinnerID);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * onItemSelected for the send and recieve spinner.
    * Used to change the picture and the amount text when a particular option is chosen on the wallet spinner
    * */
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
     * */
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "NOTHINGSELECTED", Toast.LENGTH_SHORT).show();

    }


}
