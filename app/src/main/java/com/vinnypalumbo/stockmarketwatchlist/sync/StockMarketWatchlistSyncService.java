package com.vinnypalumbo.stockmarketwatchlist.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Vincent on 2016-10-20.
 */

public class StockMarketWatchlistSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static StockMarketWatchlistSyncAdapter sStockMarketWatchlistSyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sStockMarketWatchlistSyncAdapter == null) {
                sStockMarketWatchlistSyncAdapter = new StockMarketWatchlistSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sStockMarketWatchlistSyncAdapter.getSyncAdapterBinder();
    }
}
