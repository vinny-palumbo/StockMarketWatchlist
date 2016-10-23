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

public class Utility {
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
}
