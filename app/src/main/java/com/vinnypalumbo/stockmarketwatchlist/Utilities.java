package com.vinnypalumbo.stockmarketwatchlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.vinnypalumbo.stockmarketwatchlist.sync.StockMarketWatchlistSyncAdapter;

/**
 * Created by Vincent on 2016-10-23.
 */

public class Utilities {
    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return
     */
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     *
     * @param c Context used to get the SharedPreferences
     * @return the stocks status integer type
     */
    @SuppressWarnings("ResourceType")
    static public @StockMarketWatchlistSyncAdapter.StocksStatus
    int getStocksStatus(Context c){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getInt(c.getString(R.string.pref_stocks_status_key), StockMarketWatchlistSyncAdapter.STOCKS_STATUS_UNKNOWN);
    }

    // Make the Current Price be displayed with 2 decimals
    public static String truncateCurrentPrice(String currentPrice){
        currentPrice = String.format("%.2f", Float.parseFloat(currentPrice));
        return currentPrice;
    }

    // Make the Dollar and Percent Variations be displayed with 2 decimals
    public static String truncateVariation(String variation, boolean isPercentChange){
        String weight = variation.substring(0,1);
        String ampersand = "";
        if (isPercentChange){
            ampersand = variation.substring(variation.length() - 1, variation.length());
            variation = variation.substring(0, variation.length() - 1);
        }
        variation = variation.substring(1, variation.length());
        double round = (double) Math.round(Double.parseDouble(variation) * 100) / 100;
        variation = String.format("%.2f", round);
        StringBuffer variationBuffer = new StringBuffer(variation);
        variationBuffer.insert(0, weight);
        variationBuffer.append(ampersand);
        variation = variationBuffer.toString();
        return variation;
    }

}
