package com.vinnypalumbo.stockmarketwatchlist.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Vincent on 2016-10-20.
 */

public class StockMarketWatchlistAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private StockMarketWatchlistAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new StockMarketWatchlistAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}