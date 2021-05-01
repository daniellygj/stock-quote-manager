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

import java.util.Collections;

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

}
