package com.stockQuoteManager.controller;

import com.stockQuoteManager.service.StockManagerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class StockManagerServiceControllerTest {

    private static final String URL = "/stockcache";

    @InjectMocks
    private StockManagerServiceController controller;

    @Mock
    private StockManagerService service;

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void refreshCacheShouldSucceed() throws Exception {
        doNothing().when(service).getStockList();

        mvc.perform(delete(URL))
                .andExpect(status().isOk());
    }
}
