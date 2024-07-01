package main.java;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Stock tea = new Stock("TEA", "Ordinary Share", new BigDecimal(0), 150);
        Stock cof = new Stock("COF", "Preferred", new BigDecimal(8), 0.04, 100);
        Stock mil = new Stock("MIL", "Ordinary Share", new BigDecimal(8), 100);
        Stock jui = new Stock("JUI", "Ordinary Share", new BigDecimal(23), 70);
        Stock wat = new Stock("WAT", "Ordinary Share", new BigDecimal(13), 250);

        Trade teaTr = new Trade(tea, new BigDecimal("23.0"), 54, "Buy");
        Trade teaTr2 = new Trade(tea, new BigDecimal("43.0"), 4, "Buy");
        Trade cofTr1 = new Trade(cof, new BigDecimal("16.5"), 43, "Sell");
        Trade cofTr2 = new Trade(cof, new BigDecimal("25.0"), 7, "Buy");
        Trade milTr = new Trade(mil, new BigDecimal("3.0"), 75, "Sell");
        Trade juiTr = new Trade(jui, new BigDecimal("7.0"), 26, "Buy");
        Trade watTr = new Trade(wat, new BigDecimal("2.0"), 47, "Sell");

        TradeProcessor tradeProcessor = new TradeProcessor();

        tradeProcessor.addTrade(teaTr);
        tradeProcessor.addTrade(cofTr1);
        tradeProcessor.addTrade(milTr);
        tradeProcessor.addTrade(juiTr);

        System.out.println(tradeProcessor.calculateAllShareIndex());

    }
}