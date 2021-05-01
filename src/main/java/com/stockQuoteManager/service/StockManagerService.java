package com.stockQuoteManager.service;

import com.stockQuoteManager.cache.InMemoryCache;
import com.stockQuoteManager.utils.exception.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class StockManagerService {

    @Autowired
    private InMemoryCache cache;

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

    public void validateStock(String stockId) {
        String stock = cache.get("stock").toString();

        try {
            if (stock.isEmpty()) {
                getStockList();
                stock = cache.get("stock").toString();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (!stock.contains(stockId)) {
            throw new Exception.InvalidStockException();
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
