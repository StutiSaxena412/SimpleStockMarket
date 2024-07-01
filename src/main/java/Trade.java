package main.java;

import main.java.enums.BuySell;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trade {
    private Stock stock;
    private BigDecimal tradedPrice;
    private int quantity;
    private BuySell buySell;
    private LocalDateTime timestamp;

    public Trade(Stock stock, BigDecimal tradedPrice, int quantity, String buySell, LocalDateTime timestamp) {
        this.stock = stock;
        this.tradedPrice = tradedPrice;
        this.quantity = quantity;
        if (buySell.equals("Buy")) {
            this.buySell = BuySell.Buy;
        } else if (buySell.equals("Sell")) {
            this.buySell = BuySell.Sell;
        }
        this.timestamp = timestamp;
    }

    public Trade(Stock stock, BigDecimal tradedPrice, int quantity, String buySell) {
        this.stock = stock;
        this.tradedPrice = tradedPrice;
        this.quantity = quantity;
        if (buySell.equals("Buy")) {
            this.buySell = BuySell.Buy;
        } else if (buySell.equals("Sell")) {
            this.buySell = BuySell.Sell;
        }
        this.timestamp = LocalDateTime.now();
    }

    public Stock getStock() {
        return stock;
    }

    public BigDecimal getTradedPrice() {
        return tradedPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BuySell getBuySell() {
        return buySell;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "main.java.Trade{"
                + "main.java.Stock:" + getStock().toString()
                + "Traded Price:" + tradedPrice
                + "Quantity:" + quantity
                + "Operation:" + buySell
                + "TimeStamp" + timestamp;
    }
}
