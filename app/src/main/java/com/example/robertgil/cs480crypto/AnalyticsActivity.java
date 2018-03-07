package com.example.robertgil.cs480crypto;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {
    TextView btc_value, eth_value, currency;
    private static final String URL_DATA =
            "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=NGN,USD,EUR,JPY,GBP,AUD,CAD,CHF,CNY,KES,GHS,UGX,ZAR,XAF,NZD,MYR,BND,GEL,RUB,INR";

    private RecyclerView recyclerView;
    private List<CardItems> cardItemsList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_analytics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



       // AppBarLayout app = (AppBarLayout)findViewById(R.id.appbar);

        btc_value = (TextView)findViewById(R.id.btc_value);
        eth_value = (TextView)findViewById(R.id.eth_value);
        currency = (TextView)findViewById(R.id.textViewCurrency);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cardItemsList = new ArrayList<>();

        loadURLData();

        adapter = new CardView(cardItemsList, this);
        recyclerView.setAdapter(adapter);

    }

    private void loadURLData() {


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        JsonObjectRequest DataRequest = new JsonObjectRequest(Request.Method.GET, URL_DATA, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject btc_values = response.getJSONObject("BTC".trim());
                            JSONObject eth_values = response.getJSONObject("ETH".trim());

                            Iterator<?> keysBTC = btc_values.keys();
                            Iterator<?> keysETH = eth_values.keys();

                            while(keysBTC.hasNext() && keysETH.hasNext()) {

                                String keyBTC = (String) keysBTC.next();
                                String keyETH = (String) keysETH.next();

                                CardItems card = new CardItems(keyBTC, btc_values.getDouble(keyBTC), eth_values.getDouble(keyETH));
                                cardItemsList.add(card);
                            }
                            adapter = new CardView(cardItemsList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AnalyticsActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(DataRequest);
    }

}

