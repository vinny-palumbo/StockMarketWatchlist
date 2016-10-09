package com.vinnypalumbo.stockmarketwatchlist;

/**
 * Created by Vincent on 2016-10-09.
 */

public class Stock {
    String stockSymbol;
    String companyName;
    double currentPrice;
    String variationPercentage;
    String variationAbsolute;

    public Stock(String stockSymbol, String companyName, double currentPrice, String variationPercentage, String variationAbsolute){
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
        this.currentPrice = currentPrice;
        this.variationPercentage = variationPercentage;
        this.variationAbsolute = variationAbsolute;
    }
}