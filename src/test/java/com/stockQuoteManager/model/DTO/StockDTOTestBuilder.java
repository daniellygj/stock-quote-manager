package com.stockQuoteManager.model.DTO;

import java.util.Map;
import java.util.Map;

import com.stockQuoteManager.model.DTO.StockDTO.StockDTOBuilder;

public class StockDTOTestBuilder {

    private static final String DEFAULT_ID = "petr3";

    private static final Map<String, String> DEFAULT_QUOTES = Map.of(
            "2019-01-01", "10",
            "2019-01-02", "11",
            "2019-01-03", "14"
    );

    private StockDTOBuilder builder = StockDTO.builder();

    public static StockDTOTestBuilder init() {
        return new StockDTOTestBuilder();
    }

    public StockDTOBuilder withDefaultValues() {
        return builder
                .quotes(DEFAULT_QUOTES)
                .id(DEFAULT_ID);
    }
}
