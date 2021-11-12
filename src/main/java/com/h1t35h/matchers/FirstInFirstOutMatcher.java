package com.h1t35h.matchers;

import com.h1t35h.entities.CompletedOrder;
import com.h1t35h.entities.Order;
import com.h1t35h.entities.OrderType;

import java.math.BigInteger;
import java.util.*;

/**
 * Matcher for FirstInFirstOut Price-Time order-matching rule, which states that:
 * "The first order in the order-book at a price level is the first order matched.
 * All orders at the same price level are filled according to time priority."
 * The exchange works like a market where lower selling prices and higher buying prices get priority.
 * @author hitesy
 */
public class FirstInFirstOutMatcher implements Matcher {

    private final PriorityQueue<Order> stockBuyQueue;
    private final PriorityQueue<Order> stockSellQueue;

    /**
     * CTor for the matcher initializes the priority queues based on the implementation.
     */
    public FirstInFirstOutMatcher() {
        // Initialize both queues
        this.stockSellQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.getPrice().equals(o2.getPrice())) {
                return o1.getOrderTime().compareTo(o2.getOrderTime());
            }
            return o1.getPrice().compareTo(o2.getPrice());
        });
        this.stockBuyQueue = new PriorityQueue<>((o1, o2) -> {
            if (o1.getPrice().equals(o2.getPrice())) {
                return o1.getOrderTime().compareTo(o2.getOrderTime());
            }
            return o2.getPrice().compareTo(o1.getPrice());
        });
    }

    /**
     * Adds an order to the stock Matcher.
     *
     * @param order order for Matcher.
     * @return list of Executed order
     */
    public List<CompletedOrder> addOrder(Order order) {
        List<CompletedOrder> completedOrders = new ArrayList<>();
        if (OrderType.buy.equals(order.getOrderType())) {
            stockBuyQueue.offer(order);
        } else {
            stockSellQueue.offer(order);
        }
        // Run matcher algo to check for any completed orders.
        while ((!stockSellQueue.isEmpty() && !stockBuyQueue.isEmpty()) &&
                (stockBuyQueue.peek().getPrice().compareTo(stockSellQueue.peek().getPrice()) >= 0)) {
            Order buyOrder = stockBuyQueue.poll();
            Order sellOrder = stockSellQueue.poll();

            /*
                Create a new Completed Order. We handle 2 cases.
                1. BuyOrder quantity  > SellOrder quantity.
                    We can remove both the orders out of the queue.
                2. SellOrder quantity > BuyOrder quantity.
                    We need to push back updated sell Order back into queue.
                3. BuyOrder quantity = SellOrder quantity.
                    We need to push back updated buy order back into queue.
             */
            BigInteger buyOrderQuantity = buyOrder.getQuantity();
            BigInteger sellOrderQuantity = sellOrder.getQuantity();
            BigInteger completedQuantity;

            if (buyOrderQuantity.compareTo(sellOrderQuantity) == 0) {
                completedQuantity = buyOrderQuantity;

            } else if (buyOrderQuantity.compareTo(sellOrderQuantity) > 0) {
                completedQuantity = sellOrderQuantity;
                Order updatedBuyOrder = buyOrder.withQuantity(buyOrderQuantity.subtract(sellOrderQuantity));
                // reinsert updated buyOrder.
                stockBuyQueue.offer(updatedBuyOrder);

            } else {
                completedQuantity = buyOrderQuantity;
                Order updatedSellOrder = sellOrder.withQuantity(sellOrderQuantity.subtract(buyOrderQuantity));
                // reinsert updated sellOrder.
                stockSellQueue.offer(updatedSellOrder);
            }
            completedOrders.add(CompletedOrder.builder()
                    .buyOrderId(buyOrder.getOrderId())
                    .sellOrderId(sellOrder.getOrderId())
                    .quantity(completedQuantity)
                    .sellPrice(sellOrder.getPrice())
                    .build());

        }
        return completedOrders;
    }

}
