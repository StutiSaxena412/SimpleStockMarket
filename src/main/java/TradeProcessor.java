package main.java;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TradeProcessor {
    private List<Trade> trades;
    private HashMap<String, List<Trade>> stocks;

    public TradeProcessor() {
        trades = new ArrayList<>();
        stocks = new HashMap<>();
    }

    public BigDecimal calculateDividendYield(Stock stock, double price) {
        if (price == 0) {
            return BigDecimal.ZERO;
        }
        if(stock.isOrdinaryShare()) {
            return stock.getLastDividend().divide(BigDecimal.valueOf(price), 3, RoundingMode.HALF_UP);
        } else {
            BigDecimal fixedDividendParValue = BigDecimal.valueOf(stock.getFixedDividend()).multiply(BigDecimal.valueOf(stock.getParValue()));
            return fixedDividendParValue.divide(BigDecimal.valueOf(price), 3, RoundingMode.HALF_UP);
        }
    }

    public BigDecimal calculatePERatio(Stock stock, double price) {    //assumed dividend referred to the last dividend
        if (stock.getLastDividend().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(price).divide(stock.getLastDividend(), 3, RoundingMode.HALF_UP);
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
        stocks.computeIfAbsent(trade.getStock().getIdentifier(), i -> new ArrayList<>())
                .add(trade);
    }

    public BigDecimal calculateWeightedStockPrice(String stock) {
        LocalDateTime now = LocalDateTime.now();
        List<Trade> trades = stocks.get(stock).stream()
                .filter(trade -> trade.getTimestamp().isAfter(now.minusMinutes(15)))
                .toList();
        BigDecimal tradePriceQuantitySum = BigDecimal.ZERO;
        int quantitySum = 0;

        for(Trade trade: trades){
            BigDecimal tradePriceQuantitySumTmp = trade.getTradedPrice().multiply(BigDecimal.valueOf(trade.getQuantity()));
            tradePriceQuantitySum = tradePriceQuantitySum.add(tradePriceQuantitySumTmp);
            quantitySum += trade.getQuantity();
        }

        if (quantitySum == 0) {
            return BigDecimal.ZERO;
        }

        return tradePriceQuantitySum.divide(BigDecimal.valueOf(quantitySum),3, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateAllShareIndex(){
        BigDecimal pricesMultiplied = new BigDecimal(1);

        for(Trade trade: trades){
            pricesMultiplied = pricesMultiplied.multiply(trade.getTradedPrice());
        }

        double pricedMultipliedParsing = pricesMultiplied.doubleValue();

        return BigDecimal.valueOf(Math.pow(pricedMultipliedParsing, 1.0 / trades.size())).setScale(3, RoundingMode.HALF_UP);
    }

    public List<Trade> getTrades() {
        return trades;
    }

    public HashMap<String, List<Trade>> getStocks() {
        return stocks;
    }
}
