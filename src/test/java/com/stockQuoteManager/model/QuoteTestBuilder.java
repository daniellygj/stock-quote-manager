package com.stockQuoteManager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.stockQuoteManager.model.Quote.QuoteBuilder;
import liquibase.pro.packaged.D;

import static java.time.ZoneOffset.UTC;

public class QuoteTestBuilder {

    private static LocalDate DEFAULT_DATE = LocalDate.of(2019, 1, 13);

    private static String DEFAULT_VALUE = "10";

    private static QuoteBuilder builder = Quote.builder();

    public static QuoteTestBuilder init() {
        return new QuoteTestBuilder();
    }

    public QuoteBuilder withDefaultValues() {
        return builder
                .date(DEFAULT_DATE)
                .value(DEFAULT_VALUE);
    }
}
