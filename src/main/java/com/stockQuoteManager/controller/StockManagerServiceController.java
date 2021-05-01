package com.stockQuoteManager.controller;

import com.stockQuoteManager.service.StockManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stockcache")
public class StockManagerServiceController {

    @Autowired
    private StockManagerService service;

    @DeleteMapping
    public void refreshCache() {
        service.getStockList();
    }
}
