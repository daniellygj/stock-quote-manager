package com.stockQuoteManager.controller;

import com.stockQuoteManager.controller.converter.StockConverter;
import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.service.StockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-quote-manager")
public class StockController {

    @Autowired
    private StockService service;

    @Autowired
    private StockConverter converter;

    @PostMapping
    @ApiOperation(value = "Add new quote")
    public StockDTO newQuote(@RequestBody StockDTO stockDTO) {
        return converter.toDTO(service.addNewQuote(stockDTO));
    }

    @GetMapping("/{quoteId}")
    @ApiOperation(value = "Find quote by id")
    public StockDTO findQuoteById(@PathVariable("quoteId") String quoteId) {
        return converter.toDTO(service.findByStockName(quoteId));
    }

    @GetMapping
    @ApiOperation(value = "Find all quotes")
    public List<StockDTO> findAllQuotes() {
        return converter.stockListToDTO(service.findAllStock());
    }

}
