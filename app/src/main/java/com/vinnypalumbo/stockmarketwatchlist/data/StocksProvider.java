package com.vinnypalumbo.stockmarketwatchlist.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Vincent on 2016-10-16.
 */

@ContentProvider(authority = StocksProvider.AUTHORITY, database = StocksDatabase.class)
public class StocksProvider {
    public static final String AUTHORITY = "com.vinnypalumbo.stockmarketwatchlist";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String STOCKS = "stocks";
    }

    private static Uri buildUri(String... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path:paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = StocksDatabase.STOCKS)
    public static class Stocks {
        @ContentUri(
                path = Path.STOCKS,
                type = "vnd.android.cursor.dir/stock"
        )
        public static final Uri CONTENT_URI = buildUri(Path.STOCKS);

        @InexactContentUri(
                name = "STOCK_ID",
                path = Path.STOCKS + "/*",
                type = "vnd.android.cursor.item/stock",
                whereColumn = StockColumns.SYMBOL,
                pathSegment = 1
        )
        public static Uri withSymbol(String symbol){
            return buildUri(Path.STOCKS, symbol);
        }
    }
}