package com.vinnypalumbo.stockmarketwatchlist.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Vincent on 2016-10-16.
 */

@Database(version = StocksDatabase.VERSION)
public class StocksDatabase {
    private StocksDatabase(){}

    public static final int VERSION = 1;

    @Table(StockColumns.class) public static final String STOCKS = "stocks";
}