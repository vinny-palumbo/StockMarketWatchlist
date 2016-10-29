package com.vinnypalumbo.stockmarketwatchlist.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;


/**
 * Created by Vincent on 2016-10-16.
 */

public class StockColumns {
    @DataType(DataType.Type.INTEGER) @PrimaryKey @AutoIncrement  public static final String _ID = "_id";
    @DataType(DataType.Type.TEXT) @NotNull public static final String SYMBOL = "symbol";
    @DataType(DataType.Type.TEXT) @NotNull public static final String NAME = "Name";
    @DataType(DataType.Type.TEXT) @NotNull public static final String CURRENT_PRICE = "Ask";
    @DataType(DataType.Type.TEXT) @NotNull public static final String PERCENT_CHANGE = "ChangeinPercent";
    @DataType(DataType.Type.TEXT) @NotNull public static final String DOLLAR_CHANGE = "Change";
    @DataType(DataType.Type.TEXT) @NotNull public static final String OPEN = "Open";
    @DataType(DataType.Type.TEXT) @NotNull public static final String PREVIOUS = "PreviousClose";
    @DataType(DataType.Type.TEXT) @NotNull public static final String DAY_RANGE = "DaysRange";
    @DataType(DataType.Type.TEXT) @NotNull public static final String YEAR_RANGE = "YearRange";
    @DataType(DataType.Type.TEXT) @NotNull public static final String TARGET = "OneyrTargetPrice";
    @DataType(DataType.Type.TEXT) @NotNull public static final String FIFTY_AVERAGE = "FiftydayMovingAverage";
    @DataType(DataType.Type.TEXT) @NotNull public static final String TWO_HUNDRED_AVERAGE = "TwoHundreddayMovingAverage";
    @DataType(DataType.Type.TEXT) @NotNull public static final String VOLUME = "Volume";
    @DataType(DataType.Type.TEXT) @NotNull public static final String AVERAGE_VOLUME = "AverageDailyVolume";
    @DataType(DataType.Type.TEXT) @NotNull public static final String BOOK_VALUE = "BookValue";
    @DataType(DataType.Type.TEXT) @NotNull public static final String MARKET_CAP = "MarketCapitalization";
    @DataType(DataType.Type.TEXT) @NotNull public static final String EBITDA = "EBITDA";
    @DataType(DataType.Type.TEXT) @NotNull public static final String PE_RATIO = "PERatio";
    @DataType(DataType.Type.TEXT) @NotNull public static final String EPS_ESTIMATE_CURRENT = "EPSEstimateCurrentYear";
    @DataType(DataType.Type.TEXT) @NotNull public static final String EPS_ACTUAL_CURRENT = "EarningsShare";
    @DataType(DataType.Type.TEXT) @NotNull public static final String EPS_ESTIMATE_NEXT = "EPSEstimateNextYear";
    @DataType(DataType.Type.TEXT) @NotNull public static final String DIVIDEND_DOLLAR = "DividendShare";
    @DataType(DataType.Type.TEXT) @NotNull public static final String DIVIDEND_YIELD = "DividendYield";
    @DataType(DataType.Type.TEXT) @NotNull public static final String SHORT_RATIO = "ShortRatio";
    @DataType(DataType.Type.TEXT) @NotNull public static final String IS_UP= "isUp";
}
