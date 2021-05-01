package com.stockQuoteManager.controller;

import com.stockQuoteManager.controller.converter.StockConverter;
import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.DTO.StockDTOTestBuilder;
import com.stockQuoteManager.model.Quote;
import com.stockQuoteManager.model.QuoteTestBuilder;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.model.StockTestBuilder;
import com.stockQuoteManager.service.StockService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.stockQuoteManager.utils.JSONParser.toJSON;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StockControllerTest {

    private static final String URL = "/api/stock-quote-manager";

    @InjectMocks
    private StockController controller;

    @Mock
    private StockService service;

    @Mock
    private StockConverter converter;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void addNewQuoteShouldSucceed() throws Exception {
        StockDTO stockDTO = StockDTOTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Quote quote = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock = StockTestBuilder
                .init()
                .withDefaultValues()
                .quotes(Collections.singletonList(quote))
                .build();

        when(service.addNewQuote(stockDTO)).thenReturn(stock);
        when(converter.toDTO(stock)).thenReturn(stockDTO);

        mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(toJSON(stockDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        is(stock.getStockName())));
    }

    @Test
    public void findQuoteByStockNameShouldSucceed() throws Exception {
        StockDTO stockDTO = StockDTOTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Quote quote = QuoteTestBuilder
                .init()
                .withDefaultValues()
                .build();

        Stock stock = StockTestBuilder
                .init()
                .withDefaultValues()
                .quotes(Collections.singletonList(quote))
                .build();

        when(service.findQuoteByStockName(stock.getStockName())).thenReturn(stock);
        when(converter.toDTO(stock)).thenReturn(stockDTO);

        mvc.perform(get(URL + "/" + stock.getStockName()).contentType(MediaType.APPLICATION_JSON).content(toJSON(stockDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        is(stock.getStockName())));
    }
}
