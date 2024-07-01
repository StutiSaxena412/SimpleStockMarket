package main.java;

import main.java.enums.StockType;

import java.math.BigDecimal;

public class Stock {
    private String identifier;
    private StockType type;
    private BigDecimal lastDividend;
    private double fixedDividend;
    private int parValue;

    public Stock(String identifier, String type, BigDecimal lastDividend, double fixedDividend, int parValue) {
        this.identifier = identifier;
        if (type.equals("Ordinary Share")) {
            this.type = StockType.OrdinaryShare;
        } else if (type.equals("Preferred")) {
            this.type = StockType.Preferred;
            this.fixedDividend = fixedDividend;
        }
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    public Stock(String identifier, String type, BigDecimal lastDividend, int parValue) {
        this.identifier = identifier;
        if (type.equals("Ordinary Share")) {
            this.type = StockType.OrdinaryShare;
        }
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    public boolean isOrdinaryShare() {
        return type == StockType.OrdinaryShare;
    }

    public String getIdentifier() {
        return identifier;
    }

    public StockType getType() {
        return type;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public void setLastDividend(BigDecimal lastDividend) {
        this.lastDividend = lastDividend;
    }

    public double getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(double fixedDividend) {
        this.fixedDividend = fixedDividend;
    }

    public int getParValue() {
        return parValue;
    }

    public void setParValue(int parValue) {
        this.parValue = parValue;
    }

    @Override
    public String toString() {
        if(type == StockType.Preferred) {
            return "main.java.Stock{"
                    + "Identifier:" + identifier
                    + "Type:" + type
                    + "Last Divident:" + lastDividend
                    + "Fixed Dividend:" + fixedDividend
                    + "Par Value:" + parValue;
        } else {
            return "main.java.Stock{"
                    + "Identifier:" + identifier
                    + "Type:" + type
                    + "Last Divident:" + lastDividend
                    + "Par Value:" + parValue;
        }
    }

}
