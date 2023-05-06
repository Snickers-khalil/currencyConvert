package com.isetr.currencyconverter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.isetr.currencyconverter.Data.ExchangeRate;
import com.isetr.currencyconverter.adapter.ExchangeRatesAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Activityconverter extends Activity {

    private String currency;
    // Retrieve the value of "currency" from the Intent that started this Activity
//    public static String url_currency_id_name = "https://m1mpdam-exam.azurewebsites.net/CurrencyExchange/ExchangeRates/";

    private ExchangeRatesAdapter adapter;
    private List<String> exchangeRatesList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        Intent intent = getIntent();
        currency = intent.getStringExtra("currency");

        // Initialize the exchangeRatesList and adapter
        exchangeRatesList = new ArrayList<>();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exchangeRatesList);
        adapter = new ExchangeRatesAdapter(this, exchangeRatesList);

        // Set the adapter for the ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Make the API request to retrieve the exchange rates
        getExchangeRates();
    }

    private void getExchangeRates() {
        // Create an instance of the OkHttp client
        OkHttpClient client = new OkHttpClient();

        // Create a new request to the API endpoint
        Request request = new Request.Builder()
                .url("https://m1mpdam-exam.azurewebsites.net/CurrencyExchange/ExchangeRates/" + currency + "/1/")
                .get()
                .addHeader("accept", "application/json")
                .build();
        // Use the client to send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                // Parse the response body and update the UI with the retrieved data
                                                try (ResponseBody responseBody = response.body()) {
                                                    if (!response.isSuccessful()) {
                                                        throw new IOException("Unexpected response code: " + response);
                                                    }
                                                    // Convert the JSON response to a string
                                                    String responseString = responseBody.string();

                                                    try {
                                                        JSONObject responseObj = new JSONObject(responseString);
                                                        JSONObject ratesObj = responseObj.getJSONObject("rates");

                                                        Iterator<String> keys = ratesObj.keys();
                                                        while(keys.hasNext()) {
                                                            String currencyCode = keys.next();
                                                            String amount = "1";
                                                            double rate = ratesObj.getDouble(currencyCode);
                                                            exchangeRatesList.add(String.valueOf(new ExchangeRate(currencyCode,amount, rate)));
                                                        }
                                                        // Update the adapter on the UI thread
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                adapter.notifyDataSetChanged();
                                                            }
                                                        });

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                    // Parse the JSON string to a list of exchange rate strings
//                                                    JSONArray jsonArray = new JSONArray(responseString);
///
//                                                    try {
//                                                        JSONObject responseObj = new JSONObject(responseString);
//                                                        JSONObject ratesObj = responseObj.getJSONObject("rates");
//
//                                                        Iterator<String> keys = ratesObj.keys();
//                                                        while (keys.hasNext()) {
//                                                            String currencyCode = keys.next();
//                                                            String amount = "1";
//                                                            double rate = ratesObj.getDouble(currencyCode);
//                                                            exchangeRatesList.add(String.valueOf(new ExchangeRate(currencyCode, amount, rate)));
//                                                        }
//                                                        adapter.notifyDataSetChanged();
//                                                    } catch (JSONException e) {
//                                                        e.printStackTrace();
//                                                    }
///
//                                                    for (int i = 0; i < jsonArray.length(); i++) {
//                                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                                        String currency = jsonObject.getString("currency");
//                                                        String exchangeRate = jsonObject.getString("exchangeRate");
//                                                        exchangeRatesList.add(currency + ": " + exchangeRate);
//                                                    }

                                                    // Update the UI with the retrieved data
//                                                    runOnUiThread(new Runnable() {
//                                                        @Override
//                                                        public void run() {
//                                                            adapter.notifyDataSetChanged();
//                                                        }
//                                                    });
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                // Handle any errors that occur during the API request
                                                e.printStackTrace();
                                            }
                                        }
        );
    }
}

//            }
//
////    private class GetExchangeRates extends AsyncTask<Void, Void, Void> {
////
////        @Override
////        protected Void doInBackground(Void... voids) {
////            return null;
////        }
////
////        @Override
////        protected void onPreExecute() {
////            super.onPreExecute();
////
////            pDialog.setMessage("Please wait...");
////            pDialog.setCancelable(false);
////            pDialog.show();
////
////        }
////    }
//}
//        @Override
//        protected Void doInBackground(Void... arg0) {
//
////            String json_currencys = sh.makeServiceCall(url_currency_id_name+currency+"/1/", ServiceHandler.GET);
////
////            try {
////                jsonObj_name_id = new JSONObject(json_curncy_id_name);
////                s_ids_names = jsonObj_name_id.getJSONObject("results").toString();
////            } catch (JSONException e) {
////            }
////
////            return null;
////            private void getExchangeRates() {
//            // Create an instance of the OkHttp client
//            OkHttpClient client = new OkHttpClient();
//
//            // Create a new request to the API endpoint
//            Request request = new Request.Builder()
//                    .url("https://m1mpdam-exam.azurewebsites.net/CurrencyExchange/ExchangeRates/EUR/1/")
//                    .get()
//                    .addHeader("accept", "application/json")
//                    .build();
//
//            // Use the client to send the request asynchronously
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    // Handle any errors that occur during the API request
//                    e.printStackTrace();
////                    }
//                }
//            );
//        }
//    }

//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//
//            add_country_ids_names();
//
//        }

//    }
//
//    public void add_country_ids_names() {
//
//        s_ids_names = s_ids_names.replace("{", "");
//        s_ids_names = s_ids_names.replace("}", "");
//        s_ids_names = s_ids_names.replace("\"", "");
//
//        stok = new StringTokenizer(s_ids_names, ",");
//
//
//        while (stok.hasMoreElements()) {
//            temp = stok.nextElement().toString();
//
//            if (temp.indexOf("currencySymbol") != -1) {
//                temp = stok.nextElement().toString();
//            }
//
//            String split[] = temp.split(":");
//
//            temp = stok.nextElement().toString();
//
//            if (temp.indexOf("currencySymbol") != -1) {
//                temp = stok.nextElement().toString();
//
//
//            }
//
//            String split2[] = temp.split(":");
//
//            temp = null;
//
//            currences_names.add(new Currency_Names(split[2], split2[1]));
//
//
//        }
//
//        Collections.sort(currences_names, new Comparator<Currency_Names>() {
//            @Override
//            public int compare(Currency_Names n1, Currency_Names n2) {
//
//                return n1.short_name.compareTo(n2.short_name);
//            }
//        });
//
//        adaptr_listview = new Adapter_conversion_listview(Activity_conversion_listview.this, currences_names);
//        listview.setAdapter(adaptr_listview);
//        pDialog.dismiss();
//    }
//}