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

    public void onStart() {
        super.onStart();
        FetchStocksTask fetchTask = new FetchStocksTask(getActivity(), mWatchlistAdapter);
        fetchTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // use it to populate the ListView it's attached to.
        mWatchlistAdapter = new WatchlistAdapter(
                getActivity(),
                new ArrayList<Stock>()
        );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.listview_watchlist);
        listView.setAdapter(mWatchlistAdapter);

        return rootView;
    }
}