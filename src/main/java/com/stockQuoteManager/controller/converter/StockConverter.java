package com.stockQuoteManager.controller.converter;

import com.stockQuoteManager.model.DTO.StockDTO;
import com.stockQuoteManager.model.Quote;
import com.stockQuoteManager.model.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StockConverter {

    public static StockDTO toDTO(Stock stock) {
        return StockDTO
                .builder()
                .id(stock.getStock_name())
                .quotes(quotesToHashMap(stock.getQuotes()))
                .build();
    }

    public static Stock toModel(StockDTO stockDTO) {
        return Stock
                .builder()
                .stock_name(stockDTO.getId())
                .quotes(quotesToList(stockDTO.getQuotes()))
                .build();
    }

    private static List<Quote> quotesToList(HashMap<String, String> quoteHashMap) {
        List<Quote> quoteList = new LinkedList<>();

        for (Map.Entry<String, String> entry : quoteHashMap.entrySet()) {
            String date = entry.getKey();
            String value = entry.getValue();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale( new Locale("pt", "BR") );  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
            LocalDate localDate = LocalDate.parse(date, formatter);

            Quote quote = Quote
                    .builder()
                    .date(localDate)
                    .value(value)
                    .build();


            quoteList.add(quote);
        }

        return quoteList;
    }

    private static HashMap<String, String> quotesToHashMap(List<Quote> quoteList) {
        HashMap<String, String> quotes = new HashMap<>();

        quoteList.forEach(quote -> {
            quotes.put(quote.getDate().toString(), quote.getValue());
        });

        return quotes;
    }
}
