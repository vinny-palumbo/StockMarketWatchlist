package com.vinnypalumbo.stockmarketwatchlist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vincent on 2016-10-09.
 */

public class WatchlistAdapter extends ArrayAdapter<Stock> {

    public WatchlistAdapter(Activity context, List<Stock> watchlist){
        super(context, 0, watchlist);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Stock stock = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_watchlist, parent, false);

        TextView stockSymbolTextView = (TextView) rootView.findViewById(R.id.list_item_stock_symbol);
        stockSymbolTextView.setText(stock.stockSymbol);

        TextView companyNameTextView = (TextView) rootView.findViewById(R.id.list_item_company_name);
        companyNameTextView.setText(stock.companyName);

        TextView currentPriceTextView = (TextView) rootView.findViewById(R.id.list_item_current_price);
        currentPriceTextView.setText(stock.currentPrice);

        TextView variationPercentageTextView = (TextView) rootView.findViewById(R.id.list_item_variation_percentage);
        variationPercentageTextView.setText(stock.variationPercentage);

        TextView variationAbsoluteTextView = (TextView) rootView.findViewById(R.id.list_item_variation_absolute);
        variationAbsoluteTextView.setText(stock.variationAbsolute);


        return rootView;
    }

}