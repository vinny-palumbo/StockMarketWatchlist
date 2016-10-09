package com.vinnypalumbo.stockmarketwatchlist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vincent on 2016-10-08.
 */

public class WatchlistFragment extends Fragment {

    private WatchlistAdapter mWatchlistAdapter;

    public WatchlistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the ListView.
        Stock[] data = {
                new Stock("GOOGL", "Alphabet Inc", 800.71, "-0.3%", "-2.37$"),
                new Stock("AAPL", "Apple Inc.", 114.06, "+0.15%", "+0.17$"),
                new Stock("MSFT", "Microsoft Corporation", 57.80, "+0.1%", "+0.06$"),
                new Stock("AMZN", "Amazon.com, Inc", 839.43, "-0.26%", "-2.23$"),
                new Stock("NFLX", "Netflix, Inc.", 104.82, "-0.24%", "-0.25$"),
                new Stock("FB", "Facebook Inc", 128.99, "+0.19%", "+0.25$"),
                new Stock("TWTR", "Twitter Inc", 19.85, "-0.1%", "-0.02$")
        };
        List<Stock> watchlist = new ArrayList<Stock>(Arrays.asList(data));

        // use it to populate the ListView it's attached to.
        mWatchlistAdapter = new WatchlistAdapter(
                getActivity(),
                watchlist
        );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_watchlist);
        listView.setAdapter(mWatchlistAdapter);

        return rootView;
    }
}