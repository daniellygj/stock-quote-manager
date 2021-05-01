package com.stockQuoteManager.controller.converter;

import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.DTO.StockDTOTestBuilder;
import com.stockQuoteManager.model.Quote;
import com.stockQuoteManager.model.QuoteTestBuilder;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.model.StockTestBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class StockConverterTest {

    @InjectMocks
    private StockConverter converter;

    @Test
    public void shouldConvertToDTO() {
        Quote quote = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock = StockTestBuilder
                .init()
                .withDefaultValues()
                .quotes(Collections.singletonList(quote))
                .build();

        StockDTO stockDTO = converter.toDTO(stock);

        assertEquals(stock.getStockName(), stockDTO.getId());
        assertEquals(stock.getQuotes().get(0).getDate().toString(), stockDTO.getQuotes().entrySet().iterator().next().getKey());
        assertEquals(stock.getQuotes().get(0).getValue(), stockDTO.getQuotes().entrySet().iterator().next().getValue());
    }

    @Test
    public void shouldConvertToModel() {
        StockDTO stockDTO = StockDTOTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock = converter.toModel(stockDTO);

        assertEquals(stockDTO.getId(), stock.getStockName());
        assertEquals(stockDTO.getQuotes().entrySet().iterator().next().getKey(), stock.getQuotes().get(0).getDate().toString());
        assertEquals(stockDTO.getQuotes().entrySet().iterator().next().getValue(), stock.getQuotes().get(0).getValue());
    }

    @Test
    public void shouldConvertStockListToDTO() {
        Quote quote1 = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock1 = StockTestBuilder
                .init()
                .withDefaultValues()
                .quotes(Collections.singletonList(quote1))
                .build();

        Quote quote2 = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock2 = StockTestBuilder
                .init()
                .withDefaultValues()
                .quotes(Collections.singletonList(quote2))
                .build();

        Quote quote3 = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock3 = StockTestBuilder
                .init()
                .withDefaultValues()
                .quotes(Collections.singletonList(quote3))
                .build();

        List<StockDTO> stockDTO = converter.stockListToDTO(Arrays.asList(stock1, stock2, stock3));

        assertEquals(stock1.getStockName(), stockDTO.get(0).getId());
        assertEquals(stock1.getQuotes().get(0).getDate().toString(), stockDTO.get(0).getQuotes().entrySet().iterator().next().getKey());
        assertEquals(stock1.getQuotes().get(0).getValue(), stockDTO.get(0).getQuotes().entrySet().iterator().next().getValue());

        assertEquals(stock1.getStockName(), stockDTO.get(1).getId());
        assertEquals(stock1.getQuotes().get(0).getDate().toString(), stockDTO.get(1).getQuotes().entrySet().iterator().next().getKey());
        assertEquals(stock1.getQuotes().get(0).getValue(), stockDTO.get(1).getQuotes().entrySet().iterator().next().getValue());

        assertEquals(stock1.getStockName(), stockDTO.get(2).getId());
        assertEquals(stock1.getQuotes().get(0).getDate().toString(), stockDTO.get(2).getQuotes().entrySet().iterator().next().getKey());
        assertEquals(stock1.getQuotes().get(0).getValue(), stockDTO.get(2).getQuotes().entrySet().iterator().next().getValue());
    }

}
