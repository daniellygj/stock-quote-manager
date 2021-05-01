package com.stockQuoteManager.service;

import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.stockQuoteManager.controller.converter.StockConverter.toModel;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;


    public Stock addNewQuote(StockDTO stockDTO) {
        return repository.save(toModel(stockDTO));
    }
}


