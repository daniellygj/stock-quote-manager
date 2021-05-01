package com.stockQuoteManager.service;

import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    public Stock addNewQuote(Stock stock) {
        return repository.save(stock);
    }
}


