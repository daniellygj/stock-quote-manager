package com.stockQuoteManager.utils.exception;

public class Exception {

    public static class InvalidStockException extends RuntimeException {
        public InvalidStockException() {
            super("Stock does not exists on stock-manager");
        }
    }

}
