package com.vinnypalumbo.stockmarketwatchlist;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Vincent on 2016-10-14.
 */

public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for data.
        Intent intent = getActivity().getIntent();
        if (intent != null  && intent.hasExtra("stockSymbol")
                            && intent.hasExtra("companyName")
                            && intent.hasExtra("currentPrice")
                            && intent.hasExtra("variationPercentage")
                            && intent.hasExtra("variationAbsolute")

                            && intent.hasExtra("open")
                            && intent.hasExtra("previousClose")
                            && intent.hasExtra("daysRange")
                            && intent.hasExtra("yearRange")
                            && intent.hasExtra("oneYrTargetPrice")
                            && intent.hasExtra("fiftyDayMovingAverage")
                            && intent.hasExtra("twoHundredDayMovingAverage")
                            && intent.hasExtra("volume")
                            && intent.hasExtra("averageDailyVolume")

                            && intent.hasExtra("bookValue")
                            && intent.hasExtra("marketCapitalization")
                            && intent.hasExtra("ebitda")
                            && intent.hasExtra("peRatio")
                            && intent.hasExtra("epsEstimateCurrentYear")
                            && intent.hasExtra("earningsShare")
                            && intent.hasExtra("epsEstimateNextYear")
                            && intent.hasExtra("dividend")
                            && intent.hasExtra("shortRatio")) {

            String currentPriceStr = intent.getStringExtra("currentPrice");
            TextView currentPriceTextView = (TextView) rootView.findViewById(R.id.current_price);
            currentPriceTextView.setText(currentPriceStr);

            String variationPercentageStr = intent.getStringExtra("variationPercentage");
            TextView variationPercentageTextView = (TextView) rootView.findViewById(R.id.variation_percentage);
            variationPercentageTextView.setText(variationPercentageStr);

            String variationAbsoluteStr = intent.getStringExtra("variationAbsolute");
            TextView variationAbsoluteTextView = (TextView) rootView.findViewById(R.id.variation_absolute);
            variationAbsoluteTextView.setText(variationAbsoluteStr);

            String openStr = intent.getStringExtra("open");
            TextView openTextView = (TextView) rootView.findViewById(R.id.open_value);
            openTextView.setText(openStr);

            String previousCloseStr = intent.getStringExtra("previousClose");
            TextView previousCloseTextView = (TextView) rootView.findViewById(R.id.previousClose_value);
            previousCloseTextView.setText(previousCloseStr);

            String daysRangeStr = intent.getStringExtra("daysRange");
            TextView daysRangeTextView = (TextView) rootView.findViewById(R.id.daysRange_value);
            daysRangeTextView.setText(daysRangeStr);

            String yearRangeStr = intent.getStringExtra("yearRange");
            TextView yearRangeTextView = (TextView) rootView.findViewById(R.id.yearRange_value);
            yearRangeTextView.setText(yearRangeStr);

            String oneYrTargetPriceStr = intent.getStringExtra("oneYrTargetPrice");
            TextView oneYrTargetPriceTextView = (TextView) rootView.findViewById(R.id.oneYrTargetPrice_value);
            oneYrTargetPriceTextView.setText(oneYrTargetPriceStr);

            String fiftyDayMovingAverageStr = intent.getStringExtra("fiftyDayMovingAverage");
            TextView fiftyDayMovingAverageTextView = (TextView) rootView.findViewById(R.id.fiftyDayMovingAverage_value);
            fiftyDayMovingAverageTextView.setText(fiftyDayMovingAverageStr);

            String twoHundredDayMovingAverageStr = intent.getStringExtra("twoHundredDayMovingAverage");
            TextView twoHundredDayMovingAverageTextView = (TextView) rootView.findViewById(R.id.twoHundredDayMovingAverage_value);
            twoHundredDayMovingAverageTextView.setText(twoHundredDayMovingAverageStr);

            String volumeStr = intent.getStringExtra("volume");
            TextView volumeTextView = (TextView) rootView.findViewById(R.id.volume_value);
            volumeTextView.setText(volumeStr);

            String averageDailyVolumeStr = intent.getStringExtra("averageDailyVolume");
            TextView averageDailyVolumeTextView = (TextView) rootView.findViewById(R.id.averageDailyVolume_value);
            averageDailyVolumeTextView.setText(averageDailyVolumeStr);

            String bookValueStr = intent.getStringExtra("bookValue");
            TextView bookValueTextView = (TextView) rootView.findViewById(R.id.bookValue_value);
            bookValueTextView.setText(bookValueStr);

            String marketCapitalizationStr = intent.getStringExtra("marketCapitalization");
            TextView marketCapitalizationTextView = (TextView) rootView.findViewById(R.id.marketCapitalization_value);
            marketCapitalizationTextView.setText(marketCapitalizationStr);

            String ebitdaStr = intent.getStringExtra("ebitda");
            TextView ebitdaTextView = (TextView) rootView.findViewById(R.id.ebitda_value);
            ebitdaTextView.setText(ebitdaStr);

            String peRatioStr = intent.getStringExtra("peRatio");
            TextView peRatioTextView = (TextView) rootView.findViewById(R.id.peRatio_value);
            peRatioTextView.setText(peRatioStr);

            String epsEstimateCurrentYearStr = intent.getStringExtra("epsEstimateCurrentYear");
            TextView epsEstimateCurrentYearTextView = (TextView) rootView.findViewById(R.id.epsEstimateCurrentYear_value);
            epsEstimateCurrentYearTextView.setText(epsEstimateCurrentYearStr);

            String earningsShareStr = intent.getStringExtra("earningsShare");
            TextView earningsShareTextView = (TextView) rootView.findViewById(R.id.earningsShare_value);
            earningsShareTextView.setText(earningsShareStr);

            String epsEstimateNextYearStr = intent.getStringExtra("epsEstimateNextYear");
            TextView epsEstimateNextYearTextView = (TextView) rootView.findViewById(R.id.epsEstimateNextYear_value);
            epsEstimateNextYearTextView.setText(epsEstimateNextYearStr);

            String dividendStr = intent.getStringExtra("dividend");
            TextView dividendTextView = (TextView) rootView.findViewById(R.id.dividend_value);
            dividendTextView.setText(dividendStr);

            String shortRatioStr = intent.getStringExtra("shortRatio");
            TextView shortRatioTextView = (TextView) rootView.findViewById(R.id.shortRatio_value);
            shortRatioTextView.setText(shortRatioStr);
        }

        return rootView;
    }
}
