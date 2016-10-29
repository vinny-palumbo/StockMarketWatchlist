package com.vinnypalumbo.stockmarketwatchlist.widget;

/**
 * Created by Vincent on 2016-10-22.
 */

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.vinnypalumbo.stockmarketwatchlist.R;
import com.vinnypalumbo.stockmarketwatchlist.data.StocksProvider;

import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.CURRENT_PRICE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.IS_UP;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.PERCENT_CHANGE;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns.SYMBOL;
import static com.vinnypalumbo.stockmarketwatchlist.data.StockColumns._ID;
import static com.vinnypalumbo.stockmarketwatchlist.data.StocksProvider.Stocks.CONTENT_URI;

/**
 * RemoteViewsService controlling the data being shown in the scrollable stocks collection widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CollectionWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = CollectionWidgetRemoteViewsService.class.getSimpleName();
    private static final String[] STOCKS_COLUMNS = {
        _ID,
        SYMBOL,
        CURRENT_PRICE,
        PERCENT_CHANGE,
        IS_UP
    };
    // these indices must match the projection
    static final int INDEX_STOCKS_ID = 0;
    static final int INDEX_STOCKS_SYMBOL = 1;
    static final int INDEX_STOCKS_CURRENT_PRICE = 2;
    static final int INDEX_STOCKS_PERCENT_CHANGE = 3;
    static final int INDEX_STOCKS_IS_UP = 4;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                data = getContentResolver().query(CONTENT_URI,
                        STOCKS_COLUMNS,
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_collection_list_item);

                String stockSymbol = data.getString(INDEX_STOCKS_SYMBOL);
                String currentPrice = data.getString(INDEX_STOCKS_CURRENT_PRICE);
                String variationPercentage = data.getString(INDEX_STOCKS_PERCENT_CHANGE);
                String isUp = data.getString(INDEX_STOCKS_IS_UP);

                views.setTextViewText(R.id.widget_stock_symbol, stockSymbol);
                views.setTextViewText(R.id.widget_current_price, currentPrice);
                views.setTextViewText(R.id.widget_variation_percentage, variationPercentage);

                // set list item font color depending on positive or negative variation
                int color_light;
                int color_dark;
                if(isUp.equals("yes")){
                    color_light = R.color.green_light;
                    color_dark = R.color.green_dark;
                }else{
                    color_light = R.color.red_light;
                    color_dark = R.color.red_dark;
                }
                views.setTextColor(R.id.widget_stock_symbol,getApplication().getResources().getColor(color_light));
                views.setTextColor(R.id.widget_current_price,getApplication().getResources().getColor(color_light));
                views.setTextColor(R.id.widget_variation_percentage,getApplication().getResources().getColor(color_dark));

                // Create intent to open Detail screen when widget list item is clicked
                final Intent fillInIntent = new Intent();
                Uri stockUri = StocksProvider.Stocks.withSymbol(stockSymbol);
                fillInIntent.setData(stockUri);
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_collection_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_STOCKS_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
