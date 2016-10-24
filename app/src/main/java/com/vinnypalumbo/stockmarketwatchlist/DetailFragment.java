package com.vinnypalumbo.stockmarketwatchlist;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

/**
 * Created by Vincent on 2016-10-14.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final int DETAIL_LOADER = 0;

    private static final String[] DETAIL_COLUMNS = {
            _ID,
            SYMBOL,
            NAME,
            CURRENT_PRICE,
            PERCENT_CHANGE,
            DOLLAR_CHANGE,
            OPEN,
            PREVIOUS,
            DAY_RANGE,
            YEAR_RANGE,
            TARGET,
            FIFTY_AVERAGE,
            TWO_HUNDRED_AVERAGE,
            VOLUME,
            AVERAGE_VOLUME,
            BOOK_VALUE,
            MARKET_CAP,
            EBITDA,
            PE_RATIO,
            EPS_ESTIMATE_CURRENT,
            EPS_ACTUAL_CURRENT,
            EPS_ESTIMATE_NEXT,
            DIVIDEND_DOLLAR,
            DIVIDEND_YIELD,
            SHORT_RATIO
    };

    // these constants correspond to the projection defined above, and must change if the
    // projection changes
    static final int COL_ID = 0;
    static final int COL_STOCKS_SYMBOL = 1;
    static final int COL_STOCKS_NAME = 2;
    static final int COL_STOCKS_CURRENT_PRICE = 3;
    static final int COL_STOCKS_PERCENT_CHANGE = 4;
    static final int COL_STOCKS_DOLLAR_CHANGE = 5;
    static final int COL_STOCKS_OPEN = 6;
    static final int COL_STOCKS_PREVIOUS = 7;
    static final int COL_STOCKS_DAY_RANGE = 8;
    static final int COL_STOCKS_YEAR_RANGE = 9;
    static final int COL_STOCKS_TARGET = 10;
    static final int COL_STOCKS_FIFTY_AVERAGE = 11;
    static final int COL_STOCKS_TWO_HUNDRED_AVERAGE = 12;
    static final int COL_STOCKS_VOLUME = 13;
    static final int COL_STOCKS_AVERAGE_VOLUME = 14;
    static final int COL_STOCKS_BOOK_VALUE = 15;
    static final int COL_STOCKS_MARKET_CAP = 16;
    static final int COL_STOCKS_EBITDA = 17;
    static final int COL_STOCKS_PE_RATIO = 18;
    static final int COL_STOCKS_EPS_ESTIMATE_CURRENT = 19;
    static final int COL_STOCKS_EPS_ACTUAL_CURRENT = 20;
    static final int COL_STOCKS_EPS_ESTIMATE_NEXT = 21;
    static final int COL_STOCKS_DIVIDEND_DOLLAR = 22;
    static final int COL_STOCKS_DIVIDEND_YIELD = 23;
    static final int COL_STOCKS_SHORT_RATIO = 24;

    private TextView symbolTextView;
    private TextView currentPriceTextView;
    private TextView variationPercentageTextView;
    private TextView variationAbsoluteTextView;
    private TextView openTextView;
    private TextView previousCloseTextView;
    private TextView daysRangeTextView;
    private TextView yearRangeTextView;
    private TextView oneYrTargetPriceTextView;
    private TextView fiftyDayMovingAverageTextView;
    private TextView twoHundredDayMovingAverageTextView;
    private TextView volumeTextView;
    private TextView averageDailyVolumeTextView;
    private TextView bookValueTextView;
    private TextView marketCapitalizationTextView;
    private TextView ebitdaTextView;
    private TextView peRatioTextView;
    private TextView epsEstimateCurrentYearTextView;
    private TextView earningsShareTextView;
    private TextView epsEstimateNextYearTextView;
    private TextView dividendTextView;
    private TextView shortRatioTextView;

    private AdView mAdView;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);

        symbolTextView = (TextView) rootView.findViewById(R.id.symbol);
        currentPriceTextView = (TextView) rootView.findViewById(R.id.current_price);
        variationPercentageTextView = (TextView) rootView.findViewById(R.id.variation_percentage);
        variationAbsoluteTextView = (TextView) rootView.findViewById(R.id.variation_absolute);
        openTextView = (TextView) rootView.findViewById(R.id.open_value);
        previousCloseTextView = (TextView) rootView.findViewById(R.id.previousClose_value);
        daysRangeTextView = (TextView) rootView.findViewById(R.id.daysRange_value);
        yearRangeTextView = (TextView) rootView.findViewById(R.id.yearRange_value);
        oneYrTargetPriceTextView = (TextView) rootView.findViewById(R.id.oneYrTargetPrice_value);
        fiftyDayMovingAverageTextView = (TextView) rootView.findViewById(R.id.fiftyDayMovingAverage_value);
        twoHundredDayMovingAverageTextView = (TextView) rootView.findViewById(R.id.twoHundredDayMovingAverage_value);
        volumeTextView = (TextView) rootView.findViewById(R.id.volume_value);
        averageDailyVolumeTextView = (TextView) rootView.findViewById(R.id.averageDailyVolume_value);
        bookValueTextView = (TextView) rootView.findViewById(R.id.bookValue_value);
        marketCapitalizationTextView = (TextView) rootView.findViewById(R.id.marketCapitalization_value);
        ebitdaTextView = (TextView) rootView.findViewById(R.id.ebitda_value);
        peRatioTextView = (TextView) rootView.findViewById(R.id.peRatio_value);
        epsEstimateCurrentYearTextView = (TextView) rootView.findViewById(R.id.epsEstimateCurrentYear_value);
        earningsShareTextView = (TextView) rootView.findViewById(R.id.earningsShare_value);
        epsEstimateNextYearTextView = (TextView) rootView.findViewById(R.id.epsEstimateNextYear_value);
        dividendTextView = (TextView) rootView.findViewById(R.id.dividend_value);
        shortRatioTextView = (TextView) rootView.findViewById(R.id.shortRatio_value);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return null;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                intent.getData(),
                DETAIL_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (!data.moveToFirst()) { return; }

        String stockSymbol = data.getString(COL_STOCKS_SYMBOL);
        String companyName = data.getString(COL_STOCKS_NAME);
        String currentPrice = data.getString(COL_STOCKS_CURRENT_PRICE);
        String variationPercentage = data.getString(COL_STOCKS_PERCENT_CHANGE);
        String variationAbsolute = data.getString(COL_STOCKS_DOLLAR_CHANGE);
        String open = data.getString(COL_STOCKS_OPEN);
        String previousClose = data.getString(COL_STOCKS_PREVIOUS);
        String daysRange = data.getString(COL_STOCKS_DAY_RANGE);
        String yearRange = data.getString(COL_STOCKS_YEAR_RANGE);
        String oneYrTargetPrice = data.getString(COL_STOCKS_TARGET);
        String fiftyDayMovingAverage = data.getString(COL_STOCKS_FIFTY_AVERAGE);
        String twoHundredDayMovingAverage = data.getString(COL_STOCKS_TWO_HUNDRED_AVERAGE);
        String volume = data.getString(COL_STOCKS_VOLUME);
        String averageDailyVolume = data.getString(COL_STOCKS_AVERAGE_VOLUME);
        String bookValue = data.getString(COL_STOCKS_BOOK_VALUE);
        String marketCapitalization = data.getString(COL_STOCKS_MARKET_CAP);
        String ebitda = data.getString(COL_STOCKS_EBITDA);
        String peRatio = data.getString(COL_STOCKS_PE_RATIO);
        String epsEstimateCurrentYear = data.getString(COL_STOCKS_EPS_ESTIMATE_CURRENT);
        String earningsShare = data.getString(COL_STOCKS_EPS_ACTUAL_CURRENT);
        String epsEstimateNextYear = data.getString(COL_STOCKS_EPS_ESTIMATE_NEXT);
        String dividend = "$" + data.getString(COL_STOCKS_DIVIDEND_DOLLAR) + "/" + data.getString(COL_STOCKS_DIVIDEND_YIELD) + "%";
        String shortRatio = data.getString(COL_STOCKS_SHORT_RATIO);

        symbolTextView.setText(stockSymbol);
        symbolTextView.setContentDescription(getContext().getString(R.string.accessibility_stockSymbol, stockSymbol));

        currentPriceTextView.setText(currentPrice);
        currentPriceTextView.setContentDescription(getContext().getString(R.string.accessibility_currentPrice, currentPrice));

        variationPercentageTextView.setText(variationPercentage);
        variationPercentageTextView.setContentDescription(getContext().getString(R.string.accessibility_variationPercentage, variationPercentage));

        variationAbsoluteTextView.setText(variationAbsolute);
        variationAbsoluteTextView.setContentDescription(getContext().getString(R.string.accessibility_variationAbsolute, variationAbsolute));

        openTextView.setText(open);
        openTextView.setContentDescription(getContext().getString(R.string.accessibility_open, open));

        previousCloseTextView.setText(previousClose);
        previousCloseTextView.setContentDescription(getContext().getString(R.string.accessibility_previousClose, previousClose));

        daysRangeTextView.setText(daysRange);
        daysRangeTextView.setContentDescription(getContext().getString(R.string.accessibility_daysRange, daysRange));

        yearRangeTextView.setText(yearRange);
        yearRangeTextView.setContentDescription(getContext().getString(R.string.accessibility_yearRange, yearRange));

        oneYrTargetPriceTextView.setText(oneYrTargetPrice);
        oneYrTargetPriceTextView.setContentDescription(getContext().getString(R.string.accessibility_oneYrTargetPrice, oneYrTargetPrice));

        fiftyDayMovingAverageTextView.setText(fiftyDayMovingAverage);
        fiftyDayMovingAverageTextView.setContentDescription(getContext().getString(R.string.accessibility_fiftyDayMovingAverage, fiftyDayMovingAverage));

        twoHundredDayMovingAverageTextView.setText(twoHundredDayMovingAverage);
        twoHundredDayMovingAverageTextView.setContentDescription(getContext().getString(R.string.accessibility_twoHundredDayMovingAverage, twoHundredDayMovingAverage));

        volumeTextView.setText(volume);
        volumeTextView.setContentDescription(getContext().getString(R.string.accessibility_volume, volume));

        averageDailyVolumeTextView.setText(averageDailyVolume);
        averageDailyVolumeTextView.setContentDescription(getContext().getString(R.string.accessibility_averageDailyVolume, averageDailyVolume));

        bookValueTextView.setText(bookValue);
        bookValueTextView.setContentDescription(getContext().getString(R.string.accessibility_bookValue, bookValue));

        marketCapitalizationTextView.setText(marketCapitalization);
        marketCapitalizationTextView.setContentDescription(getContext().getString(R.string.accessibility_marketCapitalization, marketCapitalization));

        ebitdaTextView.setText(ebitda);
        ebitdaTextView.setContentDescription(getContext().getString(R.string.accessibility_ebitda, ebitda));

        peRatioTextView.setText(peRatio);
        peRatioTextView.setContentDescription(getContext().getString(R.string.accessibility_peRatio, peRatio));

        epsEstimateCurrentYearTextView.setText(epsEstimateCurrentYear);
        epsEstimateCurrentYearTextView.setContentDescription(getContext().getString(R.string.accessibility_epsEstimateCurrentYear, epsEstimateCurrentYear));

        earningsShareTextView.setText(earningsShare);
        earningsShareTextView.setContentDescription(getContext().getString(R.string.accessibility_earningsShare, earningsShare));

        epsEstimateNextYearTextView.setText(epsEstimateNextYear);
        epsEstimateNextYearTextView.setContentDescription(getContext().getString(R.string.accessibility_epsEstimateNextYear, epsEstimateNextYear));

        dividendTextView.setText(dividend);
        dividendTextView.setContentDescription(getContext().getString(R.string.accessibility_dividend, dividend));

        shortRatioTextView.setText(shortRatio);
        shortRatioTextView.setContentDescription(getContext().getString(R.string.accessibility_shortRatio, shortRatio));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

}
