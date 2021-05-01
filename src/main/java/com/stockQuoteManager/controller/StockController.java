package com.stockQuoteManager.controller;

import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.stockQuoteManager.controller.converter.StockConverter.toDTO;

@RestController
@RequestMapping("/api/stock-quote-manager")
public class StockController {

    @Autowired
    private StockService service;

    @PostMapping
    @ApiOperation(value = "Add new quote")
    public StockDTO newQuote(@RequestBody StockDTO stockDTO) {
        return toDTO(service.addNewQuote(stockDTO));
    }

}
