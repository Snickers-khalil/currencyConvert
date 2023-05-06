package com.isetr.currencyconverter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.isetr.currencyconverter.R;
import java.util.List;

public class ExchangeRatesAdapter extends ArrayAdapter<String> {

    public ExchangeRatesAdapter(Context context, List<String> exchangeRates) {
        super(context, 0, exchangeRates);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String exchangeRate = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exchange_rate_item, parent, false);
        }

        // Lookup view for data population
        TextView tvExchangeRate = convertView.findViewById(R.id.tv_exchange_rate);

        // Populate the data into the template view using the data object
        tvExchangeRate.setText(exchangeRate);

        // Return the completed view to render on screen
        return convertView;
    }
}
