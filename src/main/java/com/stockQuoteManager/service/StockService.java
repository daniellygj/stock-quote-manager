package com.stockQuoteManager.service;

import com.stockQuoteManager.controller.converter.StockConverter;
import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.repository.StockRepository;
import com.stockQuoteManager.utils.exception.Exception.InvalidStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class StockService {

    private static String URL = "http://localhost:8080/stock";

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockConverter converter;


    public Stock addNewQuote(StockDTO stockDTO) {
        try {
            validateStock(stockDTO.getId());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
//        return repository.save(converter.toModel(stockDTO));
        return new Stock();
    }

    public Stock findByStockName(String quoteId) {
        return repository.findByStockName(quoteId);
    }

    public List<Stock> findAllStock() {
        return repository.findAll();
    }

    private void validateStock(String stockId) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(URL))
                .header("accept", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (!response.body().contains(stockId)) {
            throw new InvalidStockException();
        }
    }
}


