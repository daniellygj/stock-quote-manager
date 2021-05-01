package com.stockQuoteManager.service;

import com.stockQuoteManager.controller.converter.StockConverter;
import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockConverter converter;


    public Stock addNewQuote(StockDTO stockDTO) {
        return repository.save(converter.toModel(stockDTO));
    }

    public Stock findByStockName(String quoteId) {
        return repository.findByStockName(quoteId);
    }

    public List<Stock> findAllStock() {
        return repository.findAll();
    }
}


