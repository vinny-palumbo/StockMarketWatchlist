package com.vinnypalumbo.stockmarketwatchlist;

/**
 * Created by Vincent on 2016-10-09.
 */

public class Stock {
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
    String dividend;
    String shortRatio;

    public Stock(String stockSymbol, String companyName, String currentPrice, String variationPercentage, String variationAbsolute
                , String open, String previousClose, String daysRange, String yearRange, String oneYrTargetPrice, String fiftyDayMovingAverage, String twoHundredDayMovingAverage, String volume, String averageDailyVolume
                , String bookValue, String marketCapitalization, String ebitda, String peRatio, String epsEstimateCurrentYear, String earningsShare, String epsEstimateNextYear, String dividend, String shortRatio){

        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.variationPercentage = variationPercentage;
        this.variationAbsolute = variationAbsolute;

        this.open = open;
        this.previousClose = previousClose;
        this.daysRange = daysRange;
        this.yearRange = yearRange;
        this.oneYrTargetPrice = oneYrTargetPrice;
        this.fiftyDayMovingAverage = fiftyDayMovingAverage;
        this.twoHundredDayMovingAverage = twoHundredDayMovingAverage;
        this.volume = volume;
        this.averageDailyVolume = averageDailyVolume;

        this.bookValue = bookValue;
        this.marketCapitalization = marketCapitalization;
        this.ebitda = ebitda;
        this.peRatio = peRatio;
        this.epsEstimateCurrentYear = epsEstimateCurrentYear;
        this.earningsShare = earningsShare;
        this.epsEstimateNextYear = epsEstimateNextYear;
        this.dividend = dividend;
        this.shortRatio = shortRatio;
    }
}