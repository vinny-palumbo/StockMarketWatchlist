package com.vinnypalumbo.stockmarketwatchlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vinnypalumbo.stockmarketwatchlist.sync.StockMarketWatchlistSyncAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StockMarketWatchlistSyncAdapter.initializeSyncAdapter(this);
    }
}
