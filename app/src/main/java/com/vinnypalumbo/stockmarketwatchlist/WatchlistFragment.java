package com.vinnypalumbo.stockmarketwatchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vinnypalumbo.stockmarketwatchlist.data.StocksProvider;
import com.vinnypalumbo.stockmarketwatchlist.sync.StockMarketWatchlistSyncAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.AVERAGE_VOLUME;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.BOOK_VALUE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.CURRENT_PRICE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.DAY_RANGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.DIVIDEND_DOLLAR;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.DIVIDEND_YIELD;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.DOLLAR_CHANGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.EBITDA;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.EPS_ACTUAL_CURRENT;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.EPS_ESTIMATE_CURRENT;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.EPS_ESTIMATE_NEXT;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.FIFTY_AVERAGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.IS_UP;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.MARKET_CAP;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.NAME;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.OPEN;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.PERCENT_CHANGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.PE_RATIO;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.PREVIOUS;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.SHORT_RATIO;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.SYMBOL;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.TARGET;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.TWO_HUNDRED_AVERAGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.VOLUME;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.YEAR_RANGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns._ID;
import static com.vinnypalumbo.stockmarketwatchlist.data.StocksProvider.Stocks.CONTENT_URI;

/**
 * Created by Vincent on 2016-10-08.
 */

public class WatchlistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int WATCHLIST_LOADER = 0;

    // For the watchlist view we're showing only a small subset of the stored data.
    // Specify the columns we need.
    private static final String[] STOCKS_COLUMNS = {
        _ID,
        SYMBOL,
        NAME,
        CURRENT_PRICE,
        PERCENT_CHANGE,
        IS_UP
    };

    // These indices are tied to STOCKS_COLUMNS.  If STOCKS_COLUMNS changes, these
    // must change.
    static final int COL_ID = 0;
    static final int COL_STOCKS_SYMBOL = 1;
    static final int COL_STOCKS_NAME = 2;
    static final int COL_STOCKS_CURRENT_PRICE = 3;
    static final int COL_STOCKS_PERCENT_CHANGE = 4;
    static final int COL_STOCKS_IS_UP = 5;

    private WatchlistAdapter mWatchlistAdapter;

    public WatchlistFragment() {
    }

    public void onStart() {
        super.onStart();
        StockMarketWatchlistSyncAdapter.syncImmediately(getActivity());
    }

    @Override
    public void onResume() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sp.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // The CursorAdapter will take data from our cursor and populate the GridView.
        mWatchlistAdapter = new WatchlistAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_watchlist);
        View emptyView = rootView.findViewById(R.id.listview_watchlist_empty);
        listView.setEmptyView(emptyView);
        listView.setAdapter(mWatchlistAdapter);

        // setup on click event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class)
                            .setData(StocksProvider.Stocks.withSymbol(cursor.getString(COL_STOCKS_SYMBOL)));
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(WATCHLIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                CONTENT_URI,
                STOCKS_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mWatchlistAdapter.swapCursor(cursor);
        updateEmptyView();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mWatchlistAdapter.swapCursor(null);
    }

    /*
       Updates the empty list view with contextually relevant information that the user can
       use to determine why they aren't seeing any data.
    */
    private void updateEmptyView() {
        if ( mWatchlistAdapter.getCount() == 0 ) {
            TextView tv = (TextView) getView().findViewById(R.id.listview_watchlist_empty);
            if ( tv != null) {
                int message = R.string.empty_watchlist;
                @StockMarketWatchlistSyncAdapter.StocksStatus int stocksStatus = Utility.getStocksStatus(getActivity());
                switch (stocksStatus) {
                    case StockMarketWatchlistSyncAdapter.STOCKS_STATUS_SERVER_DOWN:
                        message = R.string.empty_watchlist_server_down;
                        break;
                    case StockMarketWatchlistSyncAdapter.STOCKS_STATUS_SERVER_INVALID:
                        message = R.string.empty_watchlist_server_error;
                        break;
                    default:
                        if (!Utility.isNetworkAvailable(getActivity()) ) {
                            message = R.string.empty_watchlist_no_network;
                        }
                }
                tv.setText(message);
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if ( key.equals(getString(R.string.pref_stocks_status_key)) ) {
            updateEmptyView();
        }
    }
}