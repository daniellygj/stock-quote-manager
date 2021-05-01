package com.stockQuoteManager.model;

import com.stockQuoteManager.model.Stock.StockBuilder;

public class StockTestBuilder {

    private static String DEFAULT_STOCK_NAME = "petr3";

    private static StockBuilder builder = Stock.builder();

    public static StockTestBuilder init() {
        return new StockTestBuilder();
    }

    public StockBuilder withDefaultValues() {
        return builder
                .stockName(DEFAULT_STOCK_NAME);
    }

}
