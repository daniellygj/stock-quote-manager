package com.stockQuoteManager.controller;

import com.stockQuoteManager.service.StockManagerService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Refresh cache list")
    public void refreshCache() {
        service.getStockList();
    }
}
