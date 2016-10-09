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

    ArrayAdapter<String> mWatchlistAdapter;

    public WatchlistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Create some dummy data for the ListView.
        String[] data = {
                "GOOGL - 800.71",
                "AAPL - 114.06",
                "MSFT - 57.80",
                "AMZN - 839.43",
                "NFLX - 104.82",
                "FB - 128.99",
                "TWTR - 19.85"
        };
        List<String> watchlist = new ArrayList<String>(Arrays.asList(data));

        // Now that we have some dummy data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy data) and
        // use it to populate the ListView it's attached to.
        mWatchlistAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_watchlist, // The name of the layout ID.
                        R.id.list_item_watchlist_textview, // The ID of the textview to populate.
                        watchlist);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_watchlist);
        listView.setAdapter(mWatchlistAdapter);

        return rootView;
    }
}