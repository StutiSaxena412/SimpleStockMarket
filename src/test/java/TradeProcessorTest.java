package test.java;

import main.java.Stock;
import main.java.Trade;
import main.java.TradeProcessor;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class TradeProcessorTest {

    private TradeProcessor tradeProcessor = new TradeProcessor();
    Stock tea = new Stock("TEA", "Ordinary Share", new BigDecimal(0), 150);
    Stock cof = new Stock("COF", "Preferred", new BigDecimal(8), 0.04, 100);
    Stock mil = new Stock("MIL", "Ordinary Share", new BigDecimal(8), 100);
    Stock jui = new Stock("JUI", "Ordinary Share", new BigDecimal(23), 70);
    Stock wat = new Stock("WAT", "Ordinary Share", new BigDecimal(13), 250);

    Trade teaTr = new Trade(tea, new BigDecimal("23.0"), 54, "Buy", LocalDateTime.now().minusMinutes(6));
    Trade teaTr2 = new Trade(tea, new BigDecimal("43.0"), 4, "Buy");
    Trade teaTr3 = new Trade(cof, new BigDecimal("16.5"), 43, "Sell", LocalDateTime.now().minusMinutes(16));
    Trade teaTr4 = new Trade(cof, new BigDecimal("25.0"), 7, "Buy", LocalDateTime.now().minusMinutes(56));
    Trade milTr = new Trade(mil, new BigDecimal("3.0"), 75, "Sell");
    Trade juiTr = new Trade(jui, new BigDecimal("7.0"), 26, "Buy");
    Trade watTr = new Trade(wat, new BigDecimal("6.0"), 47, "Sell");

    Trade watTr2 = new Trade(wat, new BigDecimal("2.0"), 0, "Sell", LocalDateTime.now().minusMinutes(56));

    Trade watTr3 = new Trade(wat, new BigDecimal("7.0"), 0, "Sell");

    @Test
    public void testCalculateDividendYieldPreferredType() {
        BigDecimal expectedDividendYield = new BigDecimal("0.055");
        BigDecimal actualDividendYield = tradeProcessor.calculateDividendYield(cof, 73);
        assertEquals(expectedDividendYield, actualDividendYield);
    }

    @Test
    public void testCalculateDividendYieldOrdinaryShareType() {
        BigDecimal expectedDividendYield = new BigDecimal("0.315");
        BigDecimal actualDividendYield = tradeProcessor.calculateDividendYield(jui, 73);
        assertEquals(expectedDividendYield, actualDividendYield);
    }

    @Test
    public void testCalculateDividendYieldZeroCase() {
        BigDecimal expectedDividendYield = BigDecimal.ZERO;
        BigDecimal actualDividendYield = tradeProcessor.calculateDividendYield(jui, 0);
        assertEquals(expectedDividendYield, actualDividendYield);
    }

    @Test
    public void testCalculatePERatio() {
        BigDecimal expectedRatio = new BigDecimal("5.375");
        BigDecimal actualRatio = tradeProcessor.calculatePERatio(mil, 43);
        assertEquals(expectedRatio, actualRatio);
    }

    @Test
    public void testCalculatePERatioZeroCase() {
        BigDecimal expectedRatio = BigDecimal.ZERO;
        BigDecimal actualRatio = tradeProcessor.calculatePERatio(tea, 100);
        assertEquals(expectedRatio, actualRatio);

        BigDecimal actualRatioZeroPrice = tradeProcessor.calculatePERatio(tea, 0);
        assertEquals(expectedRatio, actualRatioZeroPrice);
    }

    @Test
    public void testAddTrade() {
        tradeProcessor.addTrade(teaTr);
        tradeProcessor.addTrade(teaTr2);

        assertEquals(2, tradeProcessor.getTrades().size());
    }

    @Test
    public void testCalculateWeightedStockPriceNormalCase() {
        tradeProcessor.addTrade(teaTr);
        tradeProcessor.addTrade(teaTr2);
        tradeProcessor.addTrade(milTr);
        tradeProcessor.addTrade(juiTr);

        BigDecimal expectedWeightedStockPrice = new BigDecimal("24.379");

        BigDecimal weightedTeaPrice = tradeProcessor.calculateWeightedStockPrice(tea.getIdentifier());

        assertEquals(expectedWeightedStockPrice, weightedTeaPrice);
    }

    @Test
    public void testCalculateWeightedStockPriceOldTimesCase() {
        tradeProcessor.addTrade(teaTr);
        tradeProcessor.addTrade(teaTr2);
        tradeProcessor.addTrade(teaTr3);
        tradeProcessor.addTrade(teaTr4);

        BigDecimal expectedWeightedStockPrice = new BigDecimal("24.379");

        BigDecimal weightedTeaPrice = tradeProcessor.calculateWeightedStockPrice(tea.getIdentifier());

        assertEquals(expectedWeightedStockPrice, weightedTeaPrice);
    }

    @Test
    public void testCalculateWeightedStockPriceZeroCase() {
        tradeProcessor.addTrade(teaTr);
        tradeProcessor.addTrade(teaTr2);
        tradeProcessor.addTrade(milTr);
        tradeProcessor.addTrade(watTr2);

        BigDecimal expectedWeightedStockPrice = BigDecimal.ZERO;

        BigDecimal weightedWatPrice = tradeProcessor.calculateWeightedStockPrice(wat.getIdentifier());

        assertEquals(expectedWeightedStockPrice, weightedWatPrice);
    }

    @Test
    public void testGeometricMeanNormalCase() {
        tradeProcessor.addTrade(watTr);
        tradeProcessor.addTrade(watTr2);
        tradeProcessor.addTrade(watTr3);

        BigDecimal geometricMean = tradeProcessor.calculateAllShareIndex();

        BigDecimal expectedGeometricMean = new BigDecimal("4.380");

        assertEquals(expectedGeometricMean, geometricMean);

    }

    @Test
    public void testGeometricMeanSingleCalculationCase() {
        tradeProcessor.addTrade(milTr);

        BigDecimal geometricMean = tradeProcessor.calculateAllShareIndex();

        assertEquals(milTr.getTradedPrice().setScale(3, BigDecimal.ROUND_HALF_UP), geometricMean);

    }
}
