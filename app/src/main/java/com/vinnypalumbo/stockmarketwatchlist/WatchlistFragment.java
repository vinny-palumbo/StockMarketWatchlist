package com.vinnypalumbo.stockmarketwatchlist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Vincent on 2016-10-08.
 */

public class WatchlistFragment extends Fragment {

    public WatchlistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}