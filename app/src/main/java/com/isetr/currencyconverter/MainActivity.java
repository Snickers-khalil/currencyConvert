package com.isetr.currencyconverter;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner currencySpinner;
    private Button convert;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencySpinner = findViewById(R.id.currency_spinner);
        convert = (Button)findViewById(R.id.go_button);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        GetCurrencies();
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currency = (String) currencySpinner.getSelectedItem();
                Intent intent = new Intent(MainActivity.this, Activityconverter.class);
                intent.putExtra("currency", currency);
                startActivity(intent);
            }
        });

    }

    private void GetCurrencies() {
        String url = "https://m1mpdam-exam.azurewebsites.net/CurrencyExchange/Currencies/";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<String> currencies = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String currency = jsonArray.getString(i);
                        currencies.add(currency);
                    }

                    adapter.addAll(currencies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }
}