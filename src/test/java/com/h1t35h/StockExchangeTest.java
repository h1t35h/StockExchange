package com.h1t35h;

import com.h1t35h.entities.CompletedOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Needs better tests on Matcher and other components.
 * TODO to test other implementations.
 * @author hitesy
 */
public class StockExchangeTest {

    private StockExchange stockExchange;

    @Test
    public void testBase() throws IOException {
       String inputFile = "/Volumes/workplace/h1t35h/StockExchange/src/test/java/com/h1t35h/test_input";
       stockExchange = new StockExchange();
        List<CompletedOrder> completedOrders = stockExchange.main(new String[]{inputFile});
        List<String> expectedOrders = Arrays.asList("#3 237.45 90 #2",
                                            "#3 236.00 20 #6",
                                            "#4 236.00 10 #6",
                                            "#5 236.00 20 #6");
        List<String> actualOrders = completedOrders.stream().map(CompletedOrder::toString).collect(Collectors.toList());
        Assertions.assertLinesMatch(expectedOrders, actualOrders);
    }
}
