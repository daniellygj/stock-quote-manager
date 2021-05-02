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

import static java.lang.Thread.sleep;

@Service
public class StockManagerService {

    @Autowired
    private InMemoryCache cache;

    public void getStockList() {
        try {
            String stockManagerUrl = System.getenv("STOCK_MANAGER_URL");
            String url;
            if (stockManagerUrl == null) {
                url = "http://localhost:8080/notification";
            } else {
                url = "http://" + stockManagerUrl + ":8080/notification";
            }

            HttpResponse<String> response = getRequest(url);
            cache.add("stock", response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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

        if (stock.isEmpty()) {
            getStockList();
            stock = cache.get("stock").toString();
        }

        if (!stock.contains(stockId)) {
            throw new Exception.InvalidStockException();
        }
    }

    @PostConstruct
    private void register() throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String stockManager = System.getenv("STOCK_MANAGER_URL");
        String stockManagerUrl;
        if (stockManager == null) {
            stockManagerUrl = "http://localhost:8080/notification";
        } else {
            stockManagerUrl = "http://" + stockManager + ":8080/notification";
        }

        String stockQuoteManager = System.getenv("STOCK_MANAGER_SERVICE");

        String body;
        if(stockQuoteManager == null) {
            body = "{ \"host\": \"localhost\", \"port\": 8081 } ";
        } else {
            body = String.format("{ \"host\": \"%s\", \"port\": 8081  }", stockQuoteManager);
        }

        HttpRequest request = HttpRequest.newBuilder(
                URI.create(stockManagerUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        while (true) {
            try {
                client.send(request, HttpResponse.BodyHandlers.ofString());
                break;
            } catch (IOException e) {
                sleep(4000);
                System.out.println("Trying to send request to stock-manager");
            }
        }
    }
}
