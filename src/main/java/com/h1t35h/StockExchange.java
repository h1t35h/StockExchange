package com.h1t35h;

import com.h1t35h.entities.CompletedOrder;
import com.h1t35h.entities.Order;
import com.h1t35h.matchers.FirstInFirstOutMatcher;
import com.h1t35h.matchers.Matcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Primary Execution file to trigger stock matching
 */
public class StockExchange {
    private final Map<String, Matcher> stockToMatcher;

    public StockExchange() {
        stockToMatcher = new HashMap<>();
    }

    public List<CompletedOrder> main(String[] args) throws IOException {
        String filePath = args[0];
        Path path = FileSystems.getDefault().getPath(filePath);
        List<CompletedOrder> orders = new ArrayList<>();
        Files.lines(path)
                .forEachOrdered(
                        (orderString) -> {
                            Order order = Order.getOrderFromString(orderString);
                            String stock = order.getStock();
                            if (stockToMatcher.containsKey(stock)) {
                                List<CompletedOrder> completedOrders = stockToMatcher.get(stock).addOrder(order);
                                orders.addAll(completedOrders);
                                completedOrders.forEach(System.out::println);

                            } else {
                                stockToMatcher.put(stock, new FirstInFirstOutMatcher());
                                stockToMatcher.get(stock).addOrder(order);
                            }
                        }
                );
        return orders;
    }


}
