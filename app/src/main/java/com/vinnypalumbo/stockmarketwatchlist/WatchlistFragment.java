package com.vinnypalumbo.stockmarketwatchlist;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

        // setup on click event
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Stock stock = mWatchlistAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("stockSymbol", stock.stockSymbol)
                        .putExtra("companyName", stock.companyName)
                        .putExtra("currentPrice", stock.currentPrice)
                        .putExtra("variationPercentage", stock.variationPercentage)
                        .putExtra("variationAbsolute", stock.variationAbsolute)

                        .putExtra("open", stock.open)
                        .putExtra("previousClose", stock.previousClose)
                        .putExtra("daysRange", stock.daysRange)
                        .putExtra("yearRange", stock.yearRange)
                        .putExtra("oneYrTargetPrice", stock.oneYrTargetPrice)
                        .putExtra("fiftyDayMovingAverage", stock.fiftyDayMovingAverage)
                        .putExtra("twoHundredDayMovingAverage", stock.twoHundredDayMovingAverage)
                        .putExtra("volume", stock.volume)
                        .putExtra("averageDailyVolume", stock.averageDailyVolume)

                        .putExtra("bookValue", stock.bookValue)
                        .putExtra("marketCapitalization", stock.marketCapitalization)
                        .putExtra("ebitda", stock.ebitda)
                        .putExtra("peRatio", stock.peRatio)
                        .putExtra("epsEstimateCurrentYear", stock.epsEstimateCurrentYear)
                        .putExtra("earningsShare", stock.earningsShare)
                        .putExtra("epsEstimateNextYear", stock.epsEstimateNextYear)
                        .putExtra("dividend", stock.dividend)
                        .putExtra("shortRatio", stock.shortRatio);
                startActivity(intent);
            }
        });

        return rootView;
    }
}