package com.stockQuoteManager.service;

import com.stockQuoteManager.cache.InMemoryCache;
import com.stockQuoteManager.controller.converter.StockConverter;
import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.Stock;
import com.stockQuoteManager.repository.StockRepository;
import com.stockQuoteManager.utils.exception.Exception.InvalidStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockConverter converter;

    @Autowired
    private InMemoryCache cache;


    public Stock addNewQuote(StockDTO stockDTO) {
        try {
            validateStock(stockDTO.getId());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new Stock();
//        return repository.save(converter.toModel(stockDTO));
    }

    public Stock findByStockName(String quoteId) {
        return repository.findByStockName(quoteId);
    }

    public List<Stock> findAllStock() {
        return repository.findAll();
    }

    private void getStockList() throws IOException, InterruptedException {
        String url = "http://localhost:8080/stock";
        HttpResponse<String> response = getRequest(url);
        cache.add("stock", response.body());
    }

    private HttpResponse<String> getRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(url))
                .header("accept", "application/json")
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private void validateStock(String stockId) throws IOException, InterruptedException {
        String stock = cache.get("stock").toString();

        if (stock.isEmpty()) {
            getStockList();
            stock = cache.get("stock").toString();
        }

        if (!stock.contains(stockId)) {
            throw new InvalidStockException();
        }
    }

    @PostConstruct
    private void register() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "http://localhost:8080/notification";

        String body = "{\n" +
                " \"host\": \"localhost\",\n" +
                " \"port\": 8081\n" +
                "} ";

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}


