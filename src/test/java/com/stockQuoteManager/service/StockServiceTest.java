package com.stockQuoteManager.service;

import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.DTO.StockDTOTestBuilder;
import com.stockQuoteManager.model.Quote;
import com.stockQuoteManager.model.QuoteTestBuilder;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.model.StockTestBuilder;
import com.stockQuoteManager.repository.StockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    private static final Long STOCK_ID = 1L;

    @InjectMocks
    private StockService service;

    @Mock
    private StockRepository repository;

    @Test
    public void saveStockShouldSucceed() {
        StockDTO stockDTO = StockDTOTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Quote quote = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stockSaved = StockTestBuilder
                .init()
                .withDefaultValues()
                .id(STOCK_ID)
                .quotes(Collections.singletonList(quote))
                .build();

        when(repository.save(stockSaved)).thenReturn(stockSaved);

        Stock stock = service.addNewQuote(stockSaved);

        assertEquals(stockSaved.getStockName(), stock.getStockName());
        assertEquals(stockSaved.getQuotes(), stock.getQuotes());
    }
}
