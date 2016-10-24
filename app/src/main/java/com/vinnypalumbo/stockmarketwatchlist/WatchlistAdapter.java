package com.vinnypalumbo.stockmarketwatchlist;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Vincent on 2016-10-09.
 */

public class WatchlistAdapter extends CursorAdapter {

    public WatchlistAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_watchlist, parent, false);

        return view;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Read info from cursor
        String stockSymbol= cursor.getString(WatchlistFragment.COL_STOCKS_SYMBOL);
        String companyName= cursor.getString(WatchlistFragment.COL_STOCKS_NAME);
        String currentPrice= cursor.getString(WatchlistFragment.COL_STOCKS_CURRENT_PRICE);
        String variationPercentage= cursor.getString(WatchlistFragment.COL_STOCKS_PERCENT_CHANGE);
        String variationAbsolute= cursor.getString(WatchlistFragment.COL_STOCKS_DOLLAR_CHANGE);

        // Put data in TextViews
        TextView stockSymbolTextView = (TextView) view.findViewById(R.id.list_item_stock_symbol);
        stockSymbolTextView.setText(stockSymbol);
        stockSymbolTextView.setContentDescription(context.getString(R.string.accessibility_stockSymbol, stockSymbol));

        TextView companyNameTextView = (TextView) view.findViewById(R.id.list_item_company_name);
        companyNameTextView.setText(companyName);
        companyNameTextView.setContentDescription(context.getString(R.string.accessibility_companyName, companyName));

        TextView currentPriceTextView = (TextView) view.findViewById(R.id.list_item_current_price);
        currentPriceTextView.setText(currentPrice);
        currentPriceTextView.setContentDescription(context.getString(R.string.accessibility_currentPrice, currentPrice));

        TextView variationPercentageTextView = (TextView) view.findViewById(R.id.list_item_variation_percentage);
        variationPercentageTextView.setText(variationPercentage);
        variationPercentageTextView.setContentDescription(context.getString(R.string.accessibility_variationPercentage, variationPercentage));

        TextView variationAbsoluteTextView = (TextView) view.findViewById(R.id.list_item_variation_absolute);
        variationAbsoluteTextView.setText(variationAbsolute);
        variationAbsoluteTextView.setContentDescription(context.getString(R.string.accessibility_variationAbsolute, variationAbsolute));

    }
}