package com.vinnypalumbo.stockmarketwatchlist.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.vinnypalumbo.stockmarketwatchlist.R;
import com.vinnypalumbo.stockmarketwatchlist.data.StockColumns;

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
import java.util.Vector;

import static com.vinnypalumbo.stockmarketwatchlist.data.StocksProvider.Stocks.CONTENT_URI;

/**
 * Created by Vincent on 2016-10-20.
 */

public class StockMarketWatchlistSyncAdapter extends AbstractThreadedSyncAdapter {

    private final String LOG_TAG = StockMarketWatchlistSyncAdapter.class.getSimpleName();

    // Interval at which to sync with the data, in seconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    public StockMarketWatchlistSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    /**
     * Take the String representing the complete data in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     */
    private void getWatchlistDataFromJson(String watchlistJsonStr)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String QUERY = "query";
        final String RESULTS = "results";
        final String QUOTE = "quote";

        final String SYMBOL = "symbol";
        final String NAME = "Name";
        final String CURRENT_PRICE = "Ask";
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

        try {
            JSONObject watchlistJson = new JSONObject(watchlistJsonStr);
            JSONObject queryObject = watchlistJson.getJSONObject(QUERY);
            JSONObject resultsObject = queryObject.getJSONObject(RESULTS);
            JSONArray quoteArray = resultsObject.getJSONArray(QUOTE);

            // Insert the new stock information into the database
            Vector<ContentValues> cVVector = new Vector<ContentValues>(quoteArray.length());

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
                currentPrice = stockObject.getString(CURRENT_PRICE);
                variationPercentage = stockObject.getString(PERCENT_CHANGE);
                variationAbsolute = stockObject.getString(DOLLAR_CHANGE) + "$";

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

                ContentValues stockValues = new ContentValues();

                stockValues.put(StockColumns.SYMBOL, stockSymbol);
                stockValues.put(StockColumns.NAME, companyName);
                stockValues.put(StockColumns.CURRENT_PRICE, currentPrice);
                stockValues.put(StockColumns.PERCENT_CHANGE, variationPercentage);
                stockValues.put(StockColumns.DOLLAR_CHANGE, variationAbsolute);

                stockValues.put(StockColumns.OPEN, open);
                stockValues.put(StockColumns.PREVIOUS, previousClose);
                stockValues.put(StockColumns.DAY_RANGE, daysRange);
                stockValues.put(StockColumns.YEAR_RANGE, yearRange);
                stockValues.put(StockColumns.TARGET, oneYrTargetPrice);
                stockValues.put(StockColumns.FIFTY_AVERAGE, fiftyDayMovingAverage);
                stockValues.put(StockColumns.TWO_HUNDRED_AVERAGE, twoHundredDayMovingAverage);
                stockValues.put(StockColumns.VOLUME, volume);
                stockValues.put(StockColumns.AVERAGE_VOLUME, averageDailyVolume);

                stockValues.put(StockColumns.BOOK_VALUE, bookValue);
                stockValues.put(StockColumns.MARKET_CAP, marketCapitalization);
                stockValues.put(StockColumns.EBITDA, ebitda);
                stockValues.put(StockColumns.PE_RATIO, peRatio);
                stockValues.put(StockColumns.EPS_ESTIMATE_CURRENT, epsEstimateCurrentYear);
                stockValues.put(StockColumns.EPS_ACTUAL_CURRENT, earningsShare);
                stockValues.put(StockColumns.EPS_ESTIMATE_NEXT, epsEstimateNextYear);
                stockValues.put(StockColumns.DIVIDEND_DOLLAR, dividendShare);
                stockValues.put(StockColumns.DIVIDEND_YIELD, dividendYield);
                stockValues.put(StockColumns.SHORT_RATIO, shortRatio);


                cVVector.add(stockValues);
            }

            int inserted = 0;

            // add to database
            if ( cVVector.size() > 0 ) {
                // delete old data so we don't build up an endless history
                getContext().getContentResolver().delete(CONTENT_URI, null, null);
                // call bulkInsert to add the stock entries to the database
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                inserted = getContext().getContentResolver().bulkInsert(CONTENT_URI, cvArray);
            }

            Log.d(LOG_TAG, "Fetch Data Task Completed. " + inserted + " Inserted");

        }catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "Starting sync");

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
            urlStringBuilder.append(URLEncoder.encode("select * from yahoo.finance.quotes where symbol in (\"AAPL\",\"GOOG\",\"MSFT\",\"YHOO\",\"FB\",\"TWTR\",\"LNKD\",\"AMZN\",\"NFLX\",\"TSLA\",\"INTC\",\"ORCL\",\"IBM\",\"CSCO\",\"SNE\")", "UTF-8"));
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
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            watchlistJsonStr = buffer.toString();
            Log.v(LOG_TAG, "JSON string: " + watchlistJsonStr);
            getWatchlistDataFromJson(watchlistJsonStr);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the data, there's no point in attemping
            // to parse it.
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
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
        return;
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        StockMarketWatchlistSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}