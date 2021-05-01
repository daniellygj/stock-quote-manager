package com.stockQuoteManager.service;

import com.stockQuoteManager.controller.converter.StockConverter;
import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.DTO.StockDTOTestBuilder;
import com.stockQuoteManager.model.Quote;
import com.stockQuoteManager.model.QuoteTestBuilder;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.model.StockTestBuilder;
import com.stockQuoteManager.repository.StockRepository;
import com.stockQuoteManager.utils.exception.Exception.InvalidStockException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    private static final Long STOCK_ID = 1L;

    @InjectMocks
    private StockService service;

    @Mock
    private StockRepository repository;

    @Mock
    private StockConverter converter;

    @Mock
    private StockManagerService stockManager;

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
        when(converter.toModel(stockDTO)).thenReturn(stockSaved);
        doNothing().when(stockManager).validateStock(stockDTO.getId());

        Stock stock = service.addNewQuote(stockDTO);

        assertEquals(stockSaved.getStockName(), stock.getStockName());
        assertEquals(stockSaved.getQuotes(), stock.getQuotes());
    }

    @Test
    public void findByStockNameShouldSucceed() {

        Quote quote = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock = StockTestBuilder
                .init()
                .withDefaultValues()
                .id(STOCK_ID)
                .quotes(Collections.singletonList(quote))
                .build();

        when(repository.findByStockName(stock.getStockName())).thenReturn(stock);

        Stock stockSaved = service.findByStockName(stock.getStockName());

        assertEquals(stock.getStockName(), stockSaved.getStockName());
        assertEquals(stock.getQuotes(), stockSaved.getQuotes());
    }

    @Test
    public void findAllStockShouldSucceed() {

        Quote quote1 = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock1 = StockTestBuilder
                .init()
                .withDefaultValues()
                .id(STOCK_ID)
                .quotes(Collections.singletonList(quote1))
                .build();

        Quote quote2 = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock2 = StockTestBuilder
                .init()
                .withDefaultValues()
                .id(STOCK_ID)
                .quotes(Collections.singletonList(quote2))
                .build();

        when(repository.findAll()).thenReturn(Arrays.asList(stock1, stock2));

        List<Stock> stockSaved = service.findAllStock();

        assertEquals(stock1.getStockName(), stockSaved.get(0).getStockName());
        assertEquals(stock1.getQuotes(), stockSaved.get(0).getQuotes());

        assertEquals(stock2.getStockName(), stockSaved.get(1).getStockName());
        assertEquals(stock2.getQuotes(), stockSaved.get(1).getQuotes());
    }
}
