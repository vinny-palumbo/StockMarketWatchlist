package com.vinnypalumbo.stockmarketwatchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Vincent on 2016-10-10.
 */

public class FetchStocksTask extends AsyncTask<Void, Void, Stock[]> {

    private final String LOG_TAG = FetchStocksTask.class.getSimpleName();

    private WatchlistAdapter mWatchlistAdapter;
    private final Context mContext;

    public FetchStocksTask(Context context, WatchlistAdapter watchlistAdapter) {
        mContext = context;
        mWatchlistAdapter = watchlistAdapter;
    }


    /**
     * Take the String representing the complete data in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     */
    private Stock[] getWatchlistDataFromJson(String watchlistJsonStr)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String QUERY = "query";
        final String RESULTS = "results";
        final String QUOTE = "quote";

        final String SYMBOL = "symbol";
        final String NAME = "Name";
        final String PRICE = "Ask";
        final String PERCENT_CHANGE = "ChangeinPercent";
        final String DOLLAR_CHANGE = "Change";

        final String OPEN = "Open";
        final String PREVIOUS = "PreviousClose";
        final String DAY_RANGE = "DaysRange";
        final String YEAR_RANGE = "YearRange";
        final String TARGET = "OneyrTargetPrice";
        final String FIFTY_AVERAGE = "FiftydayMovingAverage";
        final String TWO_HUNDRED_AVERAGE = "TwoHundreddayMovingAverage";
        final String VOLUME = "Volume";
        final String AVERAGE_VOLUME = "AverageDailyVolume";

        final String BOOK_VALUE = "BookValue";
        final String MARKET_CAP = "MarketCapitalization";
        final String EBITDA = "EBITDA";
        final String PE_RATIO = "PERatio";
        final String EPS_ESTIMATE_CURRENT = "EPSEstimateCurrentYear";
        final String EPS_ACTUAL_CURRENT = "EarningsShare";
        final String EPS_ESTIMATE_NEXT = "EPSEstimateNextYear";
        final String DIVIDEND_DOLLAR = "DividendShare";
        final String DIVIDEND_YIELD = "DividendYield";
        final String SHORT_RATIO = "ShortRatio";

        JSONObject watchlistJson = new JSONObject(watchlistJsonStr);
        JSONObject queryObject = watchlistJson.getJSONObject(QUERY);
        JSONObject resultsObject = queryObject.getJSONObject(RESULTS);
        JSONArray quoteArray = resultsObject.getJSONArray(QUOTE);

        Stock[] resultWatchlist = new Stock[quoteArray.length()];
        for(int i = 0; i < quoteArray.length(); i++) {
            String stockSymbol;
            String companyName;
            String currentPrice;
            String variationPercentage;
            String variationAbsolute;

            String open;
            String previousClose;
            String daysRange;
            String yearRange;
            String oneYrTargetPrice;
            String fiftyDayMovingAverage;
            String twoHundredDayMovingAverage;
            String volume;
            String averageDailyVolume;

            String bookValue;
            String marketCapitalization;
            String ebitda;
            String peRatio;
            String epsEstimateCurrentYear;
            String earningsShare;
            String epsEstimateNextYear;
            String dividendShare;
            String dividendYield;
            String shortRatio;

            // To be formatted
            String dividend;


            // Get the JSON object representing the stock object
            JSONObject stockObject = quoteArray.getJSONObject(i);

            // Get the properties of each stock objects
            stockSymbol = stockObject.getString(SYMBOL);
            companyName = stockObject.getString(NAME);
            currentPrice = stockObject.getString(PRICE);
            variationPercentage = stockObject.getString(PERCENT_CHANGE);
            variationAbsolute = stockObject.getString(DOLLAR_CHANGE);

            open = stockObject.getString(OPEN);
            previousClose = stockObject.getString(PREVIOUS);
            daysRange = stockObject.getString(DAY_RANGE);
            yearRange = stockObject.getString(YEAR_RANGE);
            oneYrTargetPrice = stockObject.getString(TARGET);
            fiftyDayMovingAverage = stockObject.getString(FIFTY_AVERAGE);
            twoHundredDayMovingAverage = stockObject.getString(TWO_HUNDRED_AVERAGE);
            volume = stockObject.getString(VOLUME);
            averageDailyVolume = stockObject.getString(AVERAGE_VOLUME);

            bookValue = stockObject.getString(BOOK_VALUE);
            marketCapitalization = stockObject.getString(MARKET_CAP);
            ebitda = stockObject.getString(EBITDA);
            peRatio = stockObject.getString(PE_RATIO);
            epsEstimateCurrentYear = stockObject.getString(EPS_ESTIMATE_CURRENT);
            earningsShare = stockObject.getString(EPS_ACTUAL_CURRENT);
            epsEstimateNextYear = stockObject.getString(EPS_ESTIMATE_NEXT);
            dividendShare = stockObject.getString(DIVIDEND_DOLLAR);
            dividendYield = stockObject.getString(DIVIDEND_YIELD);
            shortRatio = stockObject.getString(SHORT_RATIO);

            // Format dividend
            dividend = "$" + dividendShare + "/" + dividendYield + "%";


            resultWatchlist[i] = new Stock(stockSymbol, companyName, currentPrice, variationPercentage, variationAbsolute
                    , open, previousClose, daysRange, yearRange, oneYrTargetPrice, fiftyDayMovingAverage, twoHundredDayMovingAverage, volume, averageDailyVolume
                    , bookValue, marketCapitalization, ebitda, peRatio, epsEstimateCurrentYear, earningsShare, epsEstimateNextYear, dividend, shortRatio);
        }

        for (Stock stock : resultWatchlist) {
            Log.v(LOG_TAG, "Stock entry: " + stock);
        }
        return resultWatchlist;
    }

    @Override
    protected Stock[] doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String watchlistJsonStr = null;

        try {
            // Construct the URL for the query
            StringBuilder urlStringBuilder = new StringBuilder();
            urlStringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=");
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol in (\"GOOG\",\"AAPL\",\"AMZN\",\"FB\",\"TWTR\")", "UTF-8"));
            urlStringBuilder.append("&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");

            URL url = new URL(urlStringBuilder.toString());

            // Create the request to Yahoo API, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            watchlistJsonStr = buffer.toString();
            Log.v(LOG_TAG, "JSON string: " + watchlistJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getWatchlistDataFromJson(watchlistJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the data.
        return null;
    }

    @Override
    protected void onPostExecute(Stock[] result) {
        if (result != null) {
            mWatchlistAdapter.clear();
            for(Stock stock : result) {
                mWatchlistAdapter.add(stock);
            }
            // New data is back from the server.
        }
    }

}